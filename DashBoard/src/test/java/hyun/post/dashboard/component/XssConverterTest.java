package hyun.post.dashboard.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XssConverterTest {

    XssConverter converter = new XssConverter();

    @Test
    void convertTest() {
        String script = "<script>console.log('hello')</script>";
        String inbound = converter.inbound(script);
        System.out.println(inbound);
    }

}