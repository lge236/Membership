package testProject.membership.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import testProject.membership.member.domain.MemberInfo;

public interface MemberRepository extends JpaRepository<MemberInfo, Long>
{
    Optional<MemberInfo> findById(String id);
    List<MemberInfo> findAll();//왜안돼

}
