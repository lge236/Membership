package testProject.membership.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import testProject.membership.member.domain.CartInfo;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.dto.CartInfoDTO;
import testProject.membership.member.dto.CartOrderDTO;
import testProject.membership.member.dto.OrderInfoDTO;
import testProject.membership.member.service.CartService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    @Autowired
    private final CartService cartService;

    @GetMapping("/myCart")
    public String getMyCart(Model model){
        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //현재 로그인 정보
        List<CartInfo> cart = cartService.findMyCart(member); //멤버의 주문목록 불러오기

        model.addAttribute("cart", cart); //멤버의 주문리스트 뷰로 전송
        return "/member/myCart";
    }

    //    @PostMapping(value = "/order", produces = "application/json") //주문하기, application/json 부분은 지워야 할 듯
    @PostMapping(value = "/addCart") //주문하기, application/json 부분은 지워야 할 듯
    public ResponseEntity addCart(CartInfoDTO infoDTO, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String name = principal.getName(); //현재 로그인 정보에서 이름 가져오기
        Long cartId; //장바구니번호 생성

        try {
            cartId = cartService.addCart(infoDTO, name); //주문 시도 및 주문번호 가져오기
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartId, HttpStatus.OK);
    }
    @PostMapping(value = "/cart/order")
    public ResponseEntity cartOrder(CartOrderDTO cartOrderDTO, Principal principal){
        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();

        if(cartOrderDTOList == null || cartOrderDTOList.size() == 0){
            return new ResponseEntity<String>("선택된 상품이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Long orderId = cartService.orderCartInfo(cartOrderDTOList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @PostMapping(value = "/deleteCart")
    public String deleteCart(Long cart_num){
        cartService.deleteById(cart_num);
        return "redirect:/myCart";
    }
//    @GetMapping("/orderDetail")
//    public String getOrderDetail(Model model){
//        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<OrderInfo> order = orderService.findById(num);
//        model.addAttribute("order", order);
//        return "order/orderListPage";
//    }
}
