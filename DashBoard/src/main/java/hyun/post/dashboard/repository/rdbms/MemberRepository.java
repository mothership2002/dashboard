package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("select m " +
            "from Member m " +
            "join fetch m.role r " +
            "where m.account = :account")
    Optional<Member> findMemberByAccount(@Param("account") String account);

    List<Member> findAllByRoleId(Long RoleId);

    Integer deleteByDeletedAtLessThanEqual(LocalDateTime nowBeforeThreeMonth);

    Optional<Member> findByIdAndAccount(Long memberId, String account);
}
