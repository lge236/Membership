package testProject.membership.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import testProject.membership.member.domain.OrderInfo;
import testProject.membership.member.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    @Autowired
    private final OrderService orderService;

    //return "/경로"; = html 문서 경로
    //ruturn "redirect: /값"; = 맵핑 액션 이름

    //(관리자)전체 주문 목록
    @GetMapping("/orders")
    public String getOrders(Model model){
        List<OrderInfo> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "/admin/orderList";
    }


//    @GetMapping("/orderDetail")
//    public String getOrderDetail(Model model){
//        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<OrderInfo> order = orderService.findById(num);
//        model.addAttribute("order", order);
//        return "order/orderListPage";
//    }
}
