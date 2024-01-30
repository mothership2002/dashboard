package hyun.post.dashboard.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class XssConverter {

    private final String[] xssExceptionList = new String[]{"password"};

    public boolean isExceptField(String value) {
        return Arrays.asList(xssExceptionList).contains(value);
    }

    /**
     * 생짜 string 들어올 때 저장 하기 위해
     * @param content
     * @return 이스케이프 처리 string
     */
    public String inbound(String content) {
        return content
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\(", "&#x28;")
                .replaceAll("\\)", "&#x29;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#x27;")
                .replaceAll("/", "&#x2F;");
    }

    // 필요한가?
    public String outbound(String content) {
        return content.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&#x28;", "\\(")
                .replaceAll("&#x29;", "\\)")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&#x27;", "'")
                .replaceAll("&#x2F;", "/");
    }

}
