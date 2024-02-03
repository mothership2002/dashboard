package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.dao.RoleDao;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.member.CustomMemberContext;
import hyun.post.dashboard.security.member.LoginSession;
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

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberDao.findByAccount(account);
        return new CustomMemberContext(member, member.getAuthorities());
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
            memberDao.removeAccessTokenAndRefreshToken(accessToken, refreshToken);
            memberDao.removeSession(account);
            return true;
        }
        return false;
    }
}
