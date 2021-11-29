package testProject.membership.member.controller;
import org.springframework.ui.Model;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.dto.MemberInfoDTO;
import testProject.membership.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    /*
    /user로 Post 요청이 들어오면
    UserServicedml의 save()를 호출한 뒤에 /login으로 이동
    */
    @PostMapping("/user")
    public String signup(MemberInfoDTO infoDto) { // 회원 추가
        memberService.save(infoDto);
        return "redirect:/login";
    }

    // 로그아웃 처리 SecurityContextLogoutHandler() 사용
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/admin/members")
    public String list(Model model){
        List<MemberInfo> members = memberService.findMembers(); //회원정보 안 뜨는 이유 로직이 잘못 됐거나 스프링 시큐리티 보안상 아이디 통한 개별 조회는 가능하지만 모든 정보의 리스트를 내보내진 않을 수도
        model.addAttribute("members", members);
        return "admin/memberList";
    }
}