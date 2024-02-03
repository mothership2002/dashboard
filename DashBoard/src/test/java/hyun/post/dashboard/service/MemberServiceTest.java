package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.security.member.LoginSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

    @Test
    void test6() {
        memberDao.saveSession("hello", "1","2", 3000L);
        LoginSession hello = memberDao.getSession("hello").get();

        boolean failCode = memberService.sessionCheck("hello", "hello");
        boolean successCode = memberService.sessionCheck("hello", hello.getSessionCode());
        boolean emptySession = memberService.sessionCheck("empty", "empty");
        assertFalse(failCode);
        assertTrue(successCode);
        assertTrue(emptySession);
    }
}
