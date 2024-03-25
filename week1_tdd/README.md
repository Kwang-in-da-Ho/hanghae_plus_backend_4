# 과제 TODOs
- [x] PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- [x] PATCH `/point/{id}/use` : 포인트를 사용한다.
- [x] GET `/point/{id}` : 포인트를 조회한다.
- [x] GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- [x] 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다.
- [ ] 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.

<hr/>

# What I Learned
## 1) TDD
* Spring Context의 Bean들을 이용한 테스트 케이스 작성 시에는 테스트 클래스에 `@SpringBootTest` 어노테이션을 붙일 것
* 의존성 주입은 `@Autowired constructor( [Beans] )`
### On Unit Testing and Integration Testing
* `Unit Test`는 Framework의 힘을 전혀 빌리지 않고 순수하게 작성한 함수 하나하나를 테스트해보는 것.
  * `@SpringBootTest` 어노테이션 사용하지 않음
* `Integreation Test`는 여러 단위 요소들이 협업하여 하나의 비즈니스 로직을 정확하게 수행하는지 테스트
  * 동시성 제어, DB 정합성 테스트 등이 이에 해당됨
### Stub vs Mock
* **Mock**
  * 예상되는 호출에 대한 행위(응답)을 명세해 놓은 객체
  * **behavior verification**을 할 시에 이용
    * 실제 동작을 정확히 하는지에 대한 검증
* **Stub**
  * 테스트 내에서 호출되었을 때 임시로 만들어진 지정된 응답을 함(canned answers to calls)
  * 테스트 내에서만 사용되고 테스트 외부에서는 호출되지 않음
  * 테스트 시 필요한 요소의 추상화 계층(interface)을 상속하는 가짜 클래스를 최소한의 기능으로만 구현해서 이용
  * **state verification** : 테스트 결과의 상태만 확인
  ```java
  /*
    상황) 주문 실패 시 메일이 정상적으로 발송되는지 확인
  */
  /*============ Stub ===========*/
  /** 테스트 시 이용하고 싶은 기능의 추상화 계층 - 메일 발송 */
  public interface MailService {
    public void send (Message msg);
  }
  /** 테스트 시 사용할 추상화 계층 기능의 구현 - 메일 발송하는 척 list에만 집어넣음 */
  public class MailServiceStub implements MailService {
    private List<Message> messages = new ArrayList<Message>();
    public void send (Message msg) {
      messages.add(msg);
    }
    public int numberSent() {
      return messages.size();
    }
  }
  /** Test code */
  class OrderStateTester {
    @Test
    void testOrderSendsMailIfUnfilled() {
      Order order = new Order(TALISKER, 51);
      MailServiceStub mailer = new MailServiceStub();
      order.setMailer(mailer);
      order.fill(warehouse);
      //발송되었다는 상태 검증
      assertEquals(1, mailer.numberSent());
    }
  }
  
  /* ============= Mock ============*/
  /** Test code */
  class OrderInteractionTester{
    @Test
    void testOrderSendsMailIfUnfilled() {
      Order order = new Order(TALISKER, 51);
      Mock warehouse = mock(Warehouse.class);
      Mock mailer = mock(MailService.class);
      order.setMailer((MailService) mailer.proxy());
  
      //실제 발송 행위 검증
      mailer.expects(once()).method("send");
      warehouse.expects(once()).method("hasInventory")
        .withAnyArguments()
        .will(returnValue(false));
      order.fill((Warehouse) warehouse.proxy());
    }
  }
  
  ```

* 참조) https://martinfowler.com/articles/mocksArentStubs.html#TheDifferenceBetweenMocksAndStubs

## 2) 동시성 제어
* (update point, register history)가 방해받지 않고 모두 완전히 이뤄저야 한다
* 허재 코치님의 코드
```kotlin
class LockHandler {
  // userId 기반으로 Lock 을 관리하는 ConcurrentHashMap
  private val lockMap = ConcurrentHashMap<Long, Lock>()

  fun <T> executeOnLock(key: Long, block: () -> T): T {
      //1. lock 객체 생성(ReentrantLock)
      val lock = lockMap.computeIfAbsent(key) { _ -> ReentrantLock() } //key에 해당하는 값이 없는 경우, ReentrantLock() 으로 Lock 값을 얻어온다

      //2. lock 획득
      val acquired = lock.tryLock(5, TimeUnit.SECONDS) //5초간 lock을 얻기 위해 시도한다
      if (!acquired) throw RuntimeException("Timeout 에러 발생")

      //3. lock 획득 후의 작업
      try {
          return block() //인자로 전달받은 코드 블럭을 실행한다
      } finally {
          lock.unlock() //lock 해제
      }
  }
}
```
### ConcurrentHashMap
* 쓰기 작업(`put()`) 시에 특정 세그멘트/버킷에 대한 lock을 사용함 (코드를 보면 `synchronized`블록이 걸려있음)
* `computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)`
  * key에 해당하는 값이 없는 경우, 전달받은 mappingFunction을 이용하여 값을 생성한다.
  * 해당 key에 대해서는 한 번에 하나의 thread만 접근 가능하다
### ReentrantLock
* `ReentrantLock(true)` : lock을 얻기 위한 경쟁이 있을 때, 가장 오래 기다린 thread에게 lock을 준다
* `tryLock(long time, TimeUnit unit)` : 주어진 시간 단위동안 lock을 얻기 위해 시도한다
### CompletableFuture
* 동시성 테스팅(Multithread, async) 환경을 테스트할 때 유용한 클래스
  ```kotlin
  fun `동시에 4번 입금 요청ㄱㄱ`() {
    // setup
    val userId = 1L
    // execute
    CompletableFuture.allOf(
      CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 100)) },
      CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 400)) },
      CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 300)) },
      CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 200)) },
    ).join() // 비동기적으로 4개의 스레드 실행 후 모두 완료된 결과 반환
    // assert
    val result = userPointService.read(userId)
    assertThat(result.point).isEqualTo(100 + 400 + 300 + 200)
  }
  ```
---

# 회고
## 1. Unit Testing과 Integration Testing에 대한 이해가 부족했음
* 내가 작성한 건 모두 Integration Test.. Unit test는 프레임웍의 힘을 빌리지 않고 정말 순수하게 함수의 정확한 동작만 확인
## 2. 동시성 문제에 대한 해법에 대한 이해 부족
* 검색법이 잘못되었나? 내가 해결하고자 하는 문제에 대한 해법을 검색으로는 찾지 못함.
* "동시성 테스팅" : 각 실행 건이 아니라 **모두 실행 후**에 결과가 올바르게 나오는지에 대해 테스트 하는 것.
