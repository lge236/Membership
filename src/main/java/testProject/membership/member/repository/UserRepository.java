package testProject.membership.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import testProject.membership.member.domain.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>
{
    Optional<UserInfo> findById(String id);
}
