package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByAccount(String account);
}
