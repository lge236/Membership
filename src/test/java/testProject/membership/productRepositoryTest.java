package testProject.membership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testProject.membership.member.domain.ProductInfo;
import testProject.membership.member.repository.ProductRepository;

import java.util.Optional;

public class productRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testRead() {
        Optional<ProductInfo> productInfo = productRepository.findById(1L);
        ProductInfo productInfo1 = productRepository.findById(1L).get();

        System.out.println(productInfo1.getProduct_name());
        System.out.println(productInfo.get().getProduct_name());
    }
}
