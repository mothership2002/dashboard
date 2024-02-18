package hyun.post.dashboard.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "TaskTimeFilter")
public class TaskTimeFilter extends OncePerRequestFilter {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        startTime.set(System.nanoTime() / 1000000);
        try {
            filterChain.doFilter(req, resp);
        } finally {
            log.info("[HttpMethod] : {}, [ProcessingTime] {}ms", req.getMethod(), (System.nanoTime() / 1000000) - startTime.get());
            startTime.remove();
        }
    }
}
