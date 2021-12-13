package testProject.membership.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testProject.membership.member.domain.ProductInfo;
import testProject.membership.member.dto.ProductInfoDTO;
import testProject.membership.member.service.ProductService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ProductController {

    @Autowired
    private final ProductService productService;

    //return "/경로"; = html 문서 경로
    //ruturn "redirect: /값"; = 맵핑 액션 이름

    @GetMapping("/products")
    public String productList(Model model){
        List<ProductInfo> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

   @PostMapping("/productUploader")
    public String productUpload(ProductInfoDTO infoDto) { // 회원 추가
        productService.save(infoDto);
        return "redirect:/products";
    }

    @GetMapping("/productDetail")
    public String getProductDetail(@RequestParam("SelectedItemNo") Long num, Model model){
        Optional<ProductInfo> product = productService.findById(num);
        model.addAttribute("product", product);
        return "product/productPage";
    }
}
