package testProject.membership.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
}
