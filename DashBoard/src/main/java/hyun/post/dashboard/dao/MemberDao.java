package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.repository.rdbms.MemberRepository;
import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final MemberRepository memberRepository;
    private final SyncLoginRepository loginTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findByAccount(String account) {
        return memberRepository.findMemberByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 존재하지 않음"));
    }



}
