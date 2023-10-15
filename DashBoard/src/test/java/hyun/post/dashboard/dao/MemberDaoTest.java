package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberDaoTest {

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    void init() {
        memberDao.save(new Member("test", "test", "test@test.test"));
    }

    @Test
    void find_member_by_account() {
        memberDao.findByAccount("test");
    }
}