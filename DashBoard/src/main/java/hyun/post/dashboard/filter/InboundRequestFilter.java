package hyun.post.dashboard.filter;

import hyun.post.dashboard.component.RequestLog;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class InboundRequestFilter extends OncePerRequestFilter {

    private final RequestLog requestLog;

    public InboundRequestFilter(RequestLog requestLog) {
        this.requestLog = requestLog;
    }

    // 로깅 포인트를 숨기니까 추적이 힘드네
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        RequestBodyCheckWrapper reqWrapper = new RequestBodyCheckWrapper(req);

        StringBuffer url = req.getRequestURL();
        String body = reqWrapper.getRequestBody();

        if (!req.getMethod().equals("GET")) {
            requestLog.inboundLog("[Request URL] : {}\n[Request Body]\n{}", url, body);
        }

        chain.doFilter(reqWrapper, resp);
    }
}
