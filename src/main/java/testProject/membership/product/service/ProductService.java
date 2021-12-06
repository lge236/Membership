package testProject.membership.product.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testProject.membership.product.domain.ProductInfo;
import testProject.membership.product.dto.ProductInfoDTO;
import testProject.membership.product.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public long save(ProductInfoDTO infoDto) { //long 이어야 하는 이유??

        return productRepository.save(ProductInfo.builder()
                //.product_num(infoDto.getProduct_num())
                .product_category(infoDto.getProduct_category())
                .product_name(infoDto.getProduct_name())
                .product_price(infoDto.getProduct_price())
                .product_stock(infoDto.getProduct_stock())
                .product_detail(infoDto.getProduct_detail())
                .product_date(infoDto.getProduct_date()).build()).getProduct_num();
    }
    //일반 상품 조회
    public Optional<ProductInfo> findOne(Long num){ //Long or String??
        return productRepository.findById(num);
    }

    //전체 상품 조회
    public List<ProductInfo> findAllProducts(){
        return productRepository.findAll();
    }

    public int updateById(Long num, String name, int price){
            return productRepository.updateById(num, name, price);
    }
}
