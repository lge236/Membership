package testProject.membership.member.controller;
import testProject.membership.member.dto.UserInfoDTO;
import testProject.membership.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    /*
    /user로 Post 요청이 들어오면
    UserServicedml의 save()를 호출한 뒤에 /login으로 이동
    */
    @PostMapping("/user")
    public String signup(UserInfoDTO infoDto) { // 회원 추가
        userService.save(infoDto);
        return "redirect:/login";
    }

    // 로그아웃 처리 SecurityContextLogoutHandler() 사용
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}