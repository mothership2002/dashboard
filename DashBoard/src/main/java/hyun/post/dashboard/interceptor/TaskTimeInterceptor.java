package hyun.post.dashboard.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

//@Slf4j(topic = "TaskTimeInterceptor")
////@Component
public class TaskTimeInterceptor implements HandlerInterceptor {
//
//    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        startTime.remove();
//        startTime.set(System.currentTimeMillis());
//        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request,
//                           HttpServletResponse response,
//                           Object handler,
//                           ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//        log.info("[Request Method] {}, [Processing Time] {} ms", request.getMethod(), System.currentTimeMillis() - startTime.get());
//        startTime.remove();
//    }
}
