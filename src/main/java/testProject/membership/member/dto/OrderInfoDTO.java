package testProject.membership.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderInfoDTO {

   @NotNull(message = "상품 정보가 없습니다.")
   private Long product_num;

   @Min(value = 1, message = "최소 주문 가능 수량은 1개 입니다.")
   @Max(value = 999, message = "최대 주문 가능 수량은 999개 입니다.")
   private int order_quantity;
}
