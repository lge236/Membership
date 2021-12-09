package testProject.membership.member.dto;

import lombok.Getter;
import lombok.Setter;
import testProject.membership.member.domain.OrderInfo;
import testProject.membership.member.domain.ProductInfo;

@Getter
@Setter
public class OrderDetailInfoDTO {

    private OrderInfo orderInfo;

    private int order_quantity;

    private int order_price;

    private String order_option;

    private ProductInfo productInfo;
}
