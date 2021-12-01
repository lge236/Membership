package testProject.membership.member.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import testProject.membership.member.domain.MemberInfo;
import testProject.membership.member.dto.MemberInfoDTO;
import testProject.membership.member.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param id
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails 를 상속받은 UserInfo 로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public MemberInfo loadUserByUsername(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException((id)));
    }

    public long save(MemberInfoDTO infoDto) { //long 이어야 하는 이유??
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return memberRepository.save(MemberInfo.builder()
                .id(infoDto.getId())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword())
                .name(infoDto.getName()).build()).getCode();
    }
    //일반 회원조회 (회원 정보 수정) -> loadUserByUsername 사용하기
    public Optional<MemberInfo> findOne(String memberId){ //Long or String??
        return memberRepository.findById(memberId);
    }

    //전체 회원 조회
    public List<MemberInfo> findMembers(){
        return memberRepository.findAll();
    }

    public int updateById(String id, String name, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 비밀번호 매치 로직 (였던것)

        if(encoder.matches(password, loadUserByUsername(id).getPassword())){
            return memberRepository.updateById(id, name);
        } else {throw new IllegalStateException("비밀번호가 일치하지 않습니다.");}
    }

    public int updatePassword(String id, String password, String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 비밀번호 매치 로직 (였던것)

        if(encoder.matches(password, loadUserByUsername(id).getPassword())){
            newPassword = encoder.encode(newPassword); //새로운 비밀번호 암호화
            return memberRepository.updatePassword(id, newPassword);
        } else {throw new IllegalStateException("비밀번호가 일치하지 않습니다.");}
    }
}
