package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.member.CustomMemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberDao.findByAccount(account);
        return new CustomMemberContext(member, member.getAuthorities());
    }

    public Boolean duplicateLoginCheck(String account) {
        return memberDao.duplicateLoginCheck(account);
    }

    @Transactional
    public Long createMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getAccount(),
                passwordEncoder.encode(memberDto.getPassword()),
                memberDto.getEmail());

        return memberDao.save(member);
    }
}
