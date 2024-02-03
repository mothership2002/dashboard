package hyun.post.dashboard.dao;

import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.TryDuplicateLoginException;
import hyun.post.dashboard.exception.WrongValue;
import hyun.post.dashboard.exception.entity.NotFoundMemberException;
import hyun.post.dashboard.model.dto.JsonWebToken;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.repository.rdbms.MemberRepository;
import hyun.post.dashboard.repository.rdbms.RoleRepository;
import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.LoginSessionRepository;
import hyun.post.dashboard.repository.redis.RefreshTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import hyun.post.dashboard.security.jwt.AccessToken;
import hyun.post.dashboard.security.jwt.LoginToken;
import hyun.post.dashboard.security.jwt.RefreshToken;
import hyun.post.dashboard.security.member.LoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final MemberRepository memberRepository;
    private final SyncLoginRepository loginTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginSessionRepository loginSessionRepository;
    private final RoleRepository roleRepository;

    public Long save(Member member) {
        return memberRepository.save(member).getId();
    }

    public void softDeleteById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        member.setDeletedAt(LocalDateTime.now());
    }

    public Integer hardDeleteByDate() {
        LocalDateTime nowBeforeThreeMonth = LocalDateTime.now().minusMonths(3);
        return memberRepository.deleteByDeletedAtLessThanEqual(nowBeforeThreeMonth);
    }

    public Member findByAccount(String account) {
        Member member = memberRepository.findMemberByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found User"));

        CustomAssert.isTrue(!isLogin(account),
                "Duplicate Login", TryDuplicateLoginException.class);
        loginTokenRepository.save(new LoginToken(account));
        return member;
    }

    // 엑세스토큰이 없을 경우 에러 약속
    public Boolean findOneByAccessToken(String accessToken) {
        return accessTokenRepository.findById(accessToken).isPresent();
    }

    public Boolean duplicateLoginCheck(String account) {
        return isLogin(account);
    }

    private Boolean isLogin(String account) {
        CustomAssert.hasText(account, "account value is null", WrongValue.class);
        return loginTokenRepository.findById(account).isPresent();
    }

    private Boolean hasAccessToken(String accessToken) {
        CustomAssert.hasText(accessToken, "accessToken value is null", WrongValue.class);
        return accessTokenRepository.findById(accessToken).isPresent();
    }

    public JsonWebToken saveToken(Member member, String accessTokenValue, String refreshTokenValue,
                                  Long accessTokenTimeToLive, Long refreshTokenTimeToLive) {
        AccessToken accessToken = new AccessToken(accessTokenValue, member.getAccount(), accessTokenTimeToLive);
        RefreshToken refreshToken = new RefreshToken(refreshTokenValue, member.getAccount(), refreshTokenTimeToLive);

        accessTokenRepository.save(accessToken);
        refreshTokenRepository.save(refreshToken);

        return new JsonWebToken(accessToken, refreshToken);
    }

    public void renewAccessToken(Member member, String beforeAccessToken, Long accessTokenTimeToLive, String renewAccessToken) {
        accessTokenRepository.deleteById(beforeAccessToken);
        accessTokenRepository.save(new AccessToken(renewAccessToken, member.getAccount(), accessTokenTimeToLive));
    }


    public Optional<LoginSession> getSession(String account) {
        return loginSessionRepository.findById(account);
    }

    public void saveSession(String account, String accessToken, String refreshToken) {
        loginSessionRepository.save(new LoginSession(account, accessToken, refreshToken, UUID.randomUUID().toString()));
    }

    public void removeSession(String account) {
        loginSessionRepository.deleteById(account);
    }

    public void removeAccessTokenAndRefreshToken(String accessToken, String refreshToken) {
        accessTokenRepository.deleteById(accessToken);
        refreshTokenRepository.deleteById(refreshToken);
    }
}
