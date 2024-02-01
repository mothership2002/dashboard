package hyun.post.dashboard.component;

import hyun.post.dashboard.common.StaticString;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Component
public class XssConverter {

    private final String[] xssExceptionArray = StaticString.XSS_EXCEPT_FILED_ARRAY;

    public boolean isExceptField(String value) {
        return Arrays.asList(xssExceptionArray).contains(value);
    }

    /**
     * 생짜 string 들어올 때 저장 하기 위해
     * @param content
     * @return 이스케이프 처리 string
     */
    public String inbound(String content) {
        if (StringUtils.hasText(content)){
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
        return null;
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
