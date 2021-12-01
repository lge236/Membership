package testProject.membership.member.controller;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.Optional;

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

    @PostMapping("/memberInfoMod")
    public String update(String password) { // 정보 수정, 다른 것들도 할 경우 어떻게 할 것인가

        MemberInfo member = (MemberInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(member.getId()+password);
        System.out.println(memberService.updateById(member.getId(), password));
        // 여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기때문에 세션값 갱신이 필요함

        //세션 등록
//        Authentication authentication = AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/logout"; //비밀번호 변경시 재로그인 하도록 하려면? 비밀번호 변경 따로 구현하기
    }


    // 로그아웃 처리 SecurityContextLogoutHandler() 사용
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/admin/members")
    public String list(Model model){
        List<MemberInfo> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "admin/memberList";
    }
}