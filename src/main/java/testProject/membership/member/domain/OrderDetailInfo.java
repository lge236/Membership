package testProject.membership.member.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Optional;

/*
Spring Data JPA
식별자를 직접 할당하여 관리
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 파라미터가 없는 생성자를 생성
@Entity
@javax.persistence.Table(name="order_detail")
@Getter
@Setter
public class OrderDetailInfo {

    @Id // 직접할당
    @javax.persistence.Id //Prime Key
    @Column(name = "order_detail_num", unique = true) //Entity Key?
    @GeneratedValue(strategy= GenerationType.IDENTITY)// DB에 위임을 통해 기본 키 생성
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_num")
    private OrderInfo orderInfo;

    private int order_quantity;

    private int order_price;

    @ManyToOne
    @JoinColumn(name="product_num")
    private ProductInfo productInfo;




    @Builder
    public OrderDetailInfo( int order_quantity, int order_price, ProductInfo productInfo, OrderInfo orderInfo) {

        this.order_quantity = order_quantity;
        this.order_price = order_price; //상품의 구입당시 가격
        this.productInfo = productInfo; //product_num
        this.orderInfo = orderInfo; //order_num
    }

    public static OrderDetailInfo createOrderDetailInfo(ProductInfo productInfo, int order_quantity){
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        orderDetailInfo.setProductInfo(productInfo);
        orderDetailInfo.setOrder_quantity(order_quantity);
        orderDetailInfo.setOrder_price(productInfo.getProduct_price());

        productInfo.removeStock(order_quantity);
        return orderDetailInfo;
    }

    public void addOrderNum(OrderInfo orderInfo){
        this.orderInfo = orderInfo;
    }

    public int getTotalPrice(){
        return order_price * order_quantity;
    }
}