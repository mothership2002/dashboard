package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findByIdAndAccount(Long memberId, String account);
}
