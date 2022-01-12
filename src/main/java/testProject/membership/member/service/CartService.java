package testProject.membership.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import testProject.membership.member.domain.*;
import testProject.membership.member.dto.CartInfoDTO;
import testProject.membership.member.exception.ProductNotFoundException;
import testProject.membership.member.repository.CartRepository;
import testProject.membership.member.repository.MemberRepository;
import testProject.membership.member.repository.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    @Autowired
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Long addCart(CartInfoDTO infoDto, String member_id) { //Long 이어야 하는 이유??
        ProductInfo productInfo = productRepository.findById(infoDto.getProduct_num()).orElseThrow(() -> new ProductNotFoundException("오류: 상품 정보가 없습니다."));
        MemberInfo memberInfo = memberRepository.findById(member_id).orElseThrow(() -> new UsernameNotFoundException(member_id));

        CartInfo cartInfo = CartInfo.createCartInfo(memberInfo, productInfo, infoDto.getCart_quantity());
        cartRepository.save(cartInfo);

        return cartInfo.getId();
    }

    //나의 장바구니
    public List<CartInfo> findMyCart(MemberInfo memberInfo) {
        return cartRepository.findByMemberInfo(memberInfo);
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
