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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberInfo member_info SET member_info.password = ?2 WHERE member_info.id = ?1") //id가 ?1인 레코드(?)를 찾아서 password 에 ?2를 대입
    int updateById(String id, String password);
}

