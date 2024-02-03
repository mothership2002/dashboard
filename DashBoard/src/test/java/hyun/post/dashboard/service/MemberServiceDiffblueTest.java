package hyun.post.dashboard.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.dao.RoleDao;
import hyun.post.dashboard.security.member.LoginSession;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MemberService.class})
@ExtendWith(SpringExtension.class)
class MemberServiceDiffblueTest {
    @MockBean
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleDao roleDao;

    /**
     * Method under test: {@link MemberService#sessionCheck(String, String)}
     */
    @Test
    void testSessionCheck() {
        // Arrange
        doNothing().when(memberDao).removeSession(Mockito.<String>any());
        Optional<LoginSession> ofResult = Optional.of(new LoginSession("3", "ABC123", "ABC123", "Session Code"));
        when(memberDao.getSession(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        boolean actualSessionCheckResult = memberService.sessionCheck("3", "Session Code");

        // Assert
        verify(memberDao).getSession(Mockito.<String>any());
        verify(memberDao).removeSession(Mockito.<String>any());
        assertTrue(actualSessionCheckResult);
    }

    /**
     * Method under test: {@link MemberService#sessionCheck(String, String)}
     */
    @Test
    void testSessionCheck2() {
        // Arrange
        doThrow(new UsernameNotFoundException("Session Code")).when(memberDao).removeSession(Mockito.<String>any());
        Optional<LoginSession> ofResult = Optional.of(new LoginSession("3", "ABC123", "ABC123", "Session Code"));
        when(memberDao.getSession(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> memberService.sessionCheck("3", "Session Code"));
        verify(memberDao).getSession(Mockito.<String>any());
        verify(memberDao).removeSession(Mockito.<String>any());
    }

    /**
     * Method under test: {@link MemberService#sessionCheck(String, String)}
     */
    @Test
    void testSessionCheck3() {
        // Arrange
        Optional<LoginSession> ofResult = Optional.of(new LoginSession("3", "ABC123", "ABC123", "42"));
        when(memberDao.getSession(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        boolean actualSessionCheckResult = memberService.sessionCheck("3", "Session Code");

        // Assert
        verify(memberDao).getSession(Mockito.<String>any());
        assertFalse(actualSessionCheckResult);
    }

    /**
     * Method under test: {@link MemberService#sessionCheck(String, String)}
     */
    @Test
    void testSessionCheck4() {
        // Arrange
        LoginSession loginSession = mock(LoginSession.class);
        when(loginSession.getSessionCode()).thenThrow(new UsernameNotFoundException("Msg"));
        Optional<LoginSession> ofResult = Optional.of(loginSession);
        when(memberDao.getSession(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> memberService.sessionCheck("3", "Session Code"));
        verify(memberDao).getSession(Mockito.<String>any());
        verify(loginSession).getSessionCode();
    }

    /**
     * Method under test: {@link MemberService#sessionCheck(String, String)}
     */
    @Test
    void testSessionCheck5() {
        // Arrange
        Optional<LoginSession> emptyResult = Optional.empty();
        when(memberDao.getSession(Mockito.<String>any())).thenReturn(emptyResult);
        new UsernameNotFoundException("Msg");

        // Act
        boolean actualSessionCheckResult = memberService.sessionCheck("3", "Session Code");

        // Assert
        verify(memberDao).getSession(Mockito.<String>any());
        assertFalse(actualSessionCheckResult);
    }

}
