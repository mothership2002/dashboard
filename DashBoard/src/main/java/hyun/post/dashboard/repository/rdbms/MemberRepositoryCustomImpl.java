package hyun.post.dashboard.repository.rdbms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.model.entity.QMember;
import hyun.post.dashboard.model.entity.QRole;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory qf;
    private final QMember member = QMember.member;
    private final QRole role = QRole.role;

    public Optional<Member> findByIdAndAccount(Long memberId, String account) {
        return Optional.ofNullable(
                qf.select(member)
                    .from(member)
                    .where(member.id.eq(memberId), member.account.eq(account))
                    .join(member.role, role).fetchJoin()
                    .fetchFirst()
            );
    }
}
