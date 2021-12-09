package testProject.membership.member.dto;

import lombok.Getter;
import lombok.Setter;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.domain.OrderDetailInfo;
import testProject.membership.member.domain.OrderInfo;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderInfoDTO {

    private MemberInfo memberInfo;

    private List<OrderDetailInfo> orderDetails;

    private LocalDateTime reg_time;

    private LocalDateTime update_time;

    private OrderInfo.OrderStatus order_status;
}
