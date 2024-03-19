# 과제 TODOs
- [ ] PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- [ ] PATCH `/point/{id}/use` : 포인트를 사용한다.
- [ ] GET `/point/{id}` : 포인트를 조회한다.
- [ ] GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- [ ] 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다.
- [ ] 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.

<hr/>

# What I Learned
* Spring Context의 Bean들을 이용한 테스트 케이스 작성 시에는 테스트 클래스에 `@SpringBootTest` 어노테이션을 붙일 것
* 의존성 주입은 `@Autowired constructor( [Beans] )`