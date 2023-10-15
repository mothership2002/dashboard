package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        return memberDao.findByAccount(account);
    }
}
