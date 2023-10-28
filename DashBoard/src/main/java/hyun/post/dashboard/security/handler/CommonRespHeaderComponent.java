package hyun.post.dashboard.security.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CommonRespHeaderComponent {

    public void addContentTypeResponse(HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    public void addHeader(HttpServletResponse response, String headerName, String token) {
        response.addHeader(headerName, "Bearer " + token);
    }
}
