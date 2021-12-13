package testProject.membership.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.domain.OrderDetailInfo;
import testProject.membership.member.domain.OrderInfo;
import testProject.membership.member.dto.OrderInfoDTO;
import testProject.membership.member.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
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
        List<OrderDetailInfo> orders = orderService.findAllDetails();

        model.addAttribute("orders", orders);
        return "/admin/orderList";
    }

    @GetMapping("/myOrders")
    public String getMyOrders(Model model){
        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDetailInfo> orders = orderService.findMyDetails(member);

        model.addAttribute("orders", orders);
        return "/order/myOrder";
    }

    @PostMapping(value = "/order", produces = "application/json")
    public ResponseEntity order(OrderInfoDTO infoDTO, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String name = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(infoDTO, name);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

//    @GetMapping("/orderDetail")
//    public String getOrderDetail(Model model){
//        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<OrderInfo> order = orderService.findById(num);
//        model.addAttribute("order", order);
//        return "order/orderListPage";
//    }
}
