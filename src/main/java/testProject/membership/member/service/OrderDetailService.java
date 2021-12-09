package testProject.membership.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testProject.membership.member.domain.OrderDetailInfo;
import testProject.membership.member.dto.OrderDetailInfoDTO;
import testProject.membership.member.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderDetailService {

    @Autowired
    private final OrderDetailRepository orderDetailRepository;

    public Long save(OrderDetailInfoDTO infoDto) { //Long 이어야 하는 이유??

        return orderDetailRepository.save(OrderDetailInfo.builder()
                .orderInfo(infoDto.getOrderInfo())
                .order_quantity(infoDto.getOrder_quantity())
                .order_price(infoDto.getOrder_price())
                .order_option(infoDto.getOrder_option())
                .productInfo(infoDto.getProductInfo()).build()).getId();
    }
    //일반 상품 조회
    public Optional<OrderDetailInfo> findById(Long num){ //Long or String??
        return orderDetailRepository.findById(num);
    }
    //전체 상품 조회
    public List<OrderDetailInfo> findAll(){return orderDetailRepository.findAll();}

    //상품 취소

}