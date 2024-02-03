package hyun.post.dashboard;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class BasicTest {

    @Test
    void uuid_test() {
        String string = UUID.randomUUID().toString();
        System.out.println(string);
    }
}
