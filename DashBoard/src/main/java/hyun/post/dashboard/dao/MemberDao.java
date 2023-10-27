package hyun.post.dashboard.dao;

import hyun.post.dashboard.exception.NotFoundAccessToken;
import hyun.post.dashboard.exception.TryDuplicateLoginException;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.repository.rdbms.MemberRepository;
import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import hyun.post.dashboard.security.jwt.AccessToken;
import hyun.post.dashboard.security.jwt.LoginToken;
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
        Member member = memberRepository.findMemberByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found User"));
        if (loginTokenRepository.findById(account).isPresent()) {
            throw new TryDuplicateLoginException("Duplicate Login");
        }
        loginTokenRepository.save(new LoginToken(account));
        return member;
    }

    public AccessToken findOneByAccessToken(String accessToken) {
        return accessTokenRepository.findById(accessToken)
                .orElseThrow(() -> new NotFoundAccessToken("Not Found accessToken"));
    }
}
