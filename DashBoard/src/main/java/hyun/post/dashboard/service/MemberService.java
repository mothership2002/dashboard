package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.dao.RoleDao;
import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.ExpiredSessionException;
import hyun.post.dashboard.exception.auth.NoMatchMemberInfoException;
import hyun.post.dashboard.model.dto.JwtDto;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.jwt.RefreshToken;
import hyun.post.dashboard.security.member.CustomMemberContext;
import hyun.post.dashboard.security.member.LoginSession;
import hyun.post.dashboard.security.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberDao.findByAccount(account);
        return new CustomMemberContext(member, member.getAuthorities());
    }

    @Transactional
    public JwtDto validateRefreshToken(String expiredAccessToken, String refreshToken) {
        Claims claims = jwtProvider.extractBody(refreshToken); // 여기서 토큰 만료 오류뜰꺼고
        RefreshToken token = memberDao.findOneByRefreshToken(refreshToken); // 안나오면 토큰 만료 오류 뜰꺼고

        if (jwtProvider.accessTokenValidate(expiredAccessToken)) {
            memberDao.deleteAccessToken(expiredAccessToken);
        }

        String account = token.getAccount();
        Long memberId = claims.get("memberId", Long.class);
        CustomAssert.isTrue(account.equals(claims.get("account", String.class)), "Not Valid Param", NoMatchMemberInfoException.class);
        JwtDto jwtDto = jwtProvider.renewAccessToken(account, memberId);

        LoginSession loginSession = memberDao.getSession(account).orElseThrow(ExpiredSessionException::new); // 확률적으로 있나
        loginSession.changeAccessToken(jwtDto.getAccessToken());
        memberDao.saveSession(loginSession);
        return jwtDto;
    }

    public Boolean duplicateLoginCheck(String account) {
        return memberDao.duplicateLoginCheck(account);
    }

    public Optional<LoginSession> getSession(String account) {
        return memberDao.getSession(account);
    }

    @Transactional
    public Long createMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getAccount(),
                passwordEncoder.encode(memberDto.getPassword()),
                memberDto.getEmail());
        member.setRole(roleDao.findOneByRoleName("user"));
        return memberDao.save(member);
    }

    @Transactional
    public void updateMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getAccount(),
                passwordEncoder.encode(memberDto.getPassword()),
                memberDto.getEmail());
        memberDao.save(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberDao.softDeleteById(memberId); // 몽고로 이관할까 고민중
    }

    public boolean sessionCheck(String account, String sessionCode) {
        Optional<LoginSession> session = memberDao.getSession(account);
        if (session.isEmpty()) {
            return true;
        }
        if (session.get().getSessionCode().equals(sessionCode)) {
            String accessToken = session.get().getAccessToken();
            String refreshToken = session.get().getRefreshToken();
            memberDao.deleteAccessTokenAndRefreshToken(accessToken, refreshToken);
            memberDao.deleteSession(account);
            return true;
        }
        return false;
    }

}
