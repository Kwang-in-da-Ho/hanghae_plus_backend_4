목 제거, 장바구니 조회

# 2. Sequence Diagrams
## 2-1. 포인트 충전
![포인트 충전.drawio.png](docs%2Fsequence_diagrams%2F%ED%8F%AC%EC%9D%B8%ED%8A%B8%20%EC%B6%A9%EC%A0%84.drawio.png)
## 2-2. 상품 조회
![상품 조회.drawio.png](docs%2Fsequence_diagrams%2F%EC%83%81%ED%92%88%20%EC%A1%B0%ED%9A%8C.drawio.png)
## 2-3. 주문
```mermaid
sequenceDiagram
    actor Customer
    participant OrderController
    participant OrderUseCase
    participant ProductComponent
    participant InventoryComponent
    participant OrderComponent
    participant PointComponent
    participant PaymentComponent
    participant OrderItemComponent
    
    Customer->>OrderController: POST /order
    OrderController ->> OrderUseCase: order(request)
    
    loop request.orderItems
        opt orderItem.quantity <= 0
            OrderUseCase -->> OrderController: InvalidOrderQuantityException()
            OrderController -->> Customer: 400 Bad Request
        end
        
        OrderUseCase ->> ProductComponent: retrieveProduct(orderItem.productId)
        ProductComponent ->> OrderUseCase: productInfo
        
        opt productInfo == null
            OrderUseCase -->> OrderController: InvalidProductException()
            OrderController -->> Customer: 400 Bad Request
        end
        
        OrderUseCase ->> InventoryComponent: retrieveProductInventory(orderItem.productId)
        InventoryComponent ->> OrderUseCase: InventoryInfo
        
        opt InventoryInfo.quantity < orderItem.quantity
            OrderUseCase -->> OrderController: OutofStockException()
            OrderController -->> Customer: 409 Conflict
        end
        
        OrderUseCase ->> OrderUseCase: remainingInventory.quantity = InventoryInfo.quantiy - orderItem.quantity
        OrderUseCase ->> InventoryComponent: updateInventory(remainingInventory)
        InventoryComponent -->> OrderUseCase: updatedInventory
        
        OrderUseCase ->> OrderUseCase: totalPrice += (orderItem.quantity * productInfo.price)
    end
    
    OrderUseCase ->> OrderComponent: insertOrder(customerId, totalPrice)
    OrderComponent -->> OrderUseCase: orderInfo
    
    OrderUseCase ->> PointComponent : retrieveCustomerPoint(customerId)
    PointComponent -->> OrderUseCase: customerPoint
    
    opt customerPoint < totalPrice
        OrderUseCase -->> OrderController: InsufficientPointException()
        OrderController -->> Customer: 409 Conflict
    end
    OrderUseCase ->> PaymentComponent: insertPayment()
    PaymentComponent -->> OrderUseCase: paymentResult
    
    opt paymentResult != "SUCCESS"
        OrderUseCase -->> OrderController: PaymentFailureException()
        OrderController -->> Customer: 500 Internal Error
    end
    
    OrderUseCase ->> OrderItemComponent: insertAllOrderItems()
    OrderItemComponent -->> OrderUseCase: orderItemInsertResult
    
    loop retries <= 3
        OrderUseCase ->> Data Platform: POST /order/statistics
    end
    
    OrderUseCase -->> OrderController: orderResult
    OrderController -->> Customer: 200 Success
```

# 3. ERD
```mermaid
erDiagram
  CUSTOMER {
    long customer_id PK
    string customer_name

  }
  POINT {
    long customer_id PK, FK
    long point
  }
  CUSTOMER ||--|o POINT : has

  PRODUCT {
    long product_id PK
    string product_name
    string category
    long price
  }
  INVENTORY {
    long product_id PK,FK
    long quantity
  }
  PRODUCT ||--|o INVENTORY: has

  ORDER {
    long order_id PK
    long customer_id FK
    datetime order_datetime
    string order_status
    long total_price
  }

  ORDER_ITEM {
    long order_item_id PK
    long order_id FK
    long product_id FK
    long order_item_price
  }
  ORDER ||--o{ ORDER_ITEM: contains
  CUSTOMER ||--o{ ORDER : places
  PRODUCT ||--o{ ORDER_ITEM: composes

  PAYMENT{
    long payment_id PK
    long customer_id FK
    long order_id FK
    string payment_method
    long pay_amount
    datetime pay_datetime
    string pay_status
  }
  CUSTOMER ||--o{ PAYMENT : makes
  PAYMENT ||--|| ORDER: fulfills
```
* 재고에 대한 I/O가 많으므로 `PRODUCT`테이블로부터 분리하여 `INVENTORY`테이블로 관리함
* 주문 시 각 주문 상품에 대한 상태가 별도로 관리될 수 있으므로 `ORDER` 테이블과 `ORDER_ITEM` 테이블을 분리하여 관리