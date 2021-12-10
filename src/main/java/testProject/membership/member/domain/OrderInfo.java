package testProject.membership.member.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/*
Spring Data JPA
식별자를 직접 할당하여 관리
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 파라미터가 없는 생성자를 생성
@Entity
@javax.persistence.Table(name="order_info")
@Getter
@Setter
public class OrderInfo {

    public enum OrderStatus{
        ORDER, CANCEL
    }
    @Id // 직접할당
    @javax.persistence.Id //Prime Key
    @Column(name = "order_num", unique = true) //Entity Key?
    @GeneratedValue(strategy= GenerationType.IDENTITY)// DB에 위임을 통해 기본 키 생성
    private Long id;

    @ManyToOne
    @JoinColumn(name="id")
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "orderInfo")
    private List<OrderDetailInfo> orderDetails = new ArrayList<>();

    private LocalDateTime reg_time;

    private LocalDateTime update_time;

    @Enumerated(EnumType.STRING)
    private OrderStatus order_status;




    @Builder
    public OrderInfo(Long id, MemberInfo memberInfo, List<OrderDetailInfo> orderDetails, LocalDateTime reg_time, LocalDateTime update_time, OrderStatus order_status) {
        this.id = id;
        this.memberInfo = memberInfo;
        this.orderDetails = orderDetails; //order_detail
        this.reg_time = reg_time;
        this.update_time=update_time;
        this.order_status = order_status;
    }

    public void addOrderDetailInfo(OrderDetailInfo orderDetailInfo){
        orderDetails.add(orderDetailInfo);
        orderDetailInfo.setOrderInfo(this);
    }

    public static OrderInfo createOrderInfo(MemberInfo memberInfo, List<OrderDetailInfo> orderDetails){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMemberInfo(memberInfo);

        for(OrderDetailInfo orderDetailInfo : orderDetails){
            orderInfo.addOrderDetailInfo(orderDetailInfo);
        }

        orderInfo.setOrder_status(OrderStatus.ORDER);
        orderInfo.setReg_time(LocalDateTime.now());
        return orderInfo;
    }

    public int getTotalPrice(){
        int totalPrice = 0;

        for(OrderDetailInfo orderDetailInfo : orderDetails){
            totalPrice += orderDetailInfo.getTotalPrice();
        }
        return totalPrice;
    }
}
