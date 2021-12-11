package testProject.membership.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.domain.OrderDetailInfo;
import testProject.membership.member.domain.OrderInfo;
import testProject.membership.member.domain.ProductInfo;
import testProject.membership.member.dto.OrderInfoDTO;
import testProject.membership.member.exception.ProductNotFoundException;
import testProject.membership.member.repository.MemberRepository;
import testProject.membership.member.repository.OrderRepository;
import testProject.membership.member.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Long order(OrderInfoDTO infoDto, String member_id) { //Long 이어야 하는 이유??
        ProductInfo productInfo = productRepository.findById(infoDto.getProduct_num()).orElseThrow(() -> new ProductNotFoundException("오류: 상품 정보가 없습니다."));
        MemberInfo memberInfo = memberRepository.findById(member_id).orElseThrow(() -> new UsernameNotFoundException(member_id));

        List<OrderDetailInfo> orderDetails = new ArrayList<>();
        OrderDetailInfo orderDetailInfo = OrderDetailInfo.createOrderDetailInfo(productInfo, infoDto.getOrder_quantity());
        orderDetails.add(orderDetailInfo);

        OrderInfo orderInfo = OrderInfo.createOrderInfo(memberInfo, orderDetails);
        orderRepository.save(orderInfo);

        return orderInfo.getId();
    }

    //일반 주문 조회
    /*public Optional<OrderInfo> findById(Long num){ //Long or String??
        return orderRepository.findById(num);
    }*/
    //전체 주문 조회
    public List<OrderInfo> findAll(){return orderRepository.findAll();}

    //상품 취소

}