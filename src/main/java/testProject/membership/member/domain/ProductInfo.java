package testProject.membership.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import testProject.membership.member.exception.OutOfStockException;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
/*
Spring Data JPA
식별자를 직접 할당하여 관리
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 파라미터가 없는 생성자를 생성
@Entity
@javax.persistence.Table(name="product_info")
@Getter
public class ProductInfo{

    @Id // 직접할당
    @javax.persistence.Id //Prime Key
    @Column(name = "product_num", unique = true) //Entity Key?
    @GeneratedValue(strategy= GenerationType.IDENTITY)// DB에 위임을 통해 기본 키 생성
    private Long id;

    private String product_category;

    @Column(unique = true)
    private String product_name;

    private int product_price;

    private int product_stock;

    private String product_detail;

    private String product_date;


    @Builder
    public ProductInfo(Long id, String product_category, String product_name, Integer product_price, Integer product_stock, String product_detail, String product_date) {
        this.id = id;
        this.product_category = product_category;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_detail = product_detail;
        this.product_date = product_date;
    }

    public void removeStock(int quantity){
        int remainStock = this.product_stock - quantity;
        if(remainStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. \n부족 수량: " + (remainStock + quantity) + ", 현재 재고: " + this.product_stock);
        }
        else
            this.product_stock = remainStock;
    }
}

