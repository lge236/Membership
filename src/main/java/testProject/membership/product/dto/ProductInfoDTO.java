package testProject.membership.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ProductInfoDTO {

    private Long product_num;
    private String product_category;
    private String product_name;
    private int product_price;
    private int product_stock;
    private String product_detail;
    private String product_date;
}
