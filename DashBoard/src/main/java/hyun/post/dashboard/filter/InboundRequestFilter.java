package hyun.post.dashboard.filter;

import hyun.post.dashboard.component.RequestLog;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class InboundRequestFilter implements Filter {

    private final RequestLog requestLog;

    public InboundRequestFilter(RequestLog requestLog) {
        this.requestLog = requestLog;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Request Body Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        RequestBodyCheckWrapper reqWrapper = new RequestBodyCheckWrapper(req);

        StringBuffer url = req.getRequestURL();
        String body = reqWrapper.getRequestBody();
        requestLog.inboundLog("[Request URL] : {}\n[Request Body]\n{}", url, body);

        chain.doFilter(reqWrapper, response);
    }
}
