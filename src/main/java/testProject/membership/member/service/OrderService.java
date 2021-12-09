package testProject.membership.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;
import testProject.membership.member.domain.OrderInfo;
import testProject.membership.member.dto.OrderInfoDTO;
import testProject.membership.member.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public Long save(OrderInfoDTO infoDto) { //Long 이어야 하는 이유??

        return orderRepository.save(OrderInfo.builder()
                .memberInfo(infoDto.getMemberInfo())
                .orderDetails(infoDto.getOrderDetails())
                .reg_time(infoDto.getReg_time())
                .update_time(infoDto.getUpdate_time())
                .order_status(infoDto.getOrder_status()).build()).getId();
    }
    //일반 상품 조회
    public Optional<OrderInfo> findById(Long num){ //Long or String??
        return orderRepository.findById(num);
    }
    //전체 상품 조회
    public List<OrderInfo> findAll(){return orderRepository.findAll();}

    //상품 취소

}