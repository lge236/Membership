package testProject.membership.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import testProject.membership.member.domain.MemberInfo;

@Repository
public interface MemberRepository extends JpaRepository<MemberInfo, Long> {

    Optional<MemberInfo> findById(String id);
    List<MemberInfo> findAll();

    //회원 정보 수정(비밀번호 제외)
    @Transactional //영속성 문제 해결
    @Modifying(clearAutomatically = true)//DB와 스프링간의 데이터 싱크를 위해(값 불일치 방지)
    @Query("UPDATE MemberInfo member_info SET member_info.name = ?2 WHERE member_info.id = ?1") //id가 ?1인 레코드(?)를 찾아서 password 에 ?2를 대입
//  다음과 같이 응용  @Query("UPDATE MemberInfo member_info SET member_info.name = ?2, member_info.email = ?3, member_info.address = ?4 WHERE member_info.id = ?1")
    int updateById(String id, String name);

    //비밀 번호 수정
    @Transactional //영속성 문제 해결
    @Modifying(clearAutomatically = true)//DB와 스프링간의 데이터 싱크를 위해(값 불일치 방지)
    @Query("UPDATE MemberInfo member_info SET member_info.password = ?2 WHERE member_info.id = ?1")
   int updatePassword(String id, String password);

}
