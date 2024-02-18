package hyun.post.dashboard.config;

import hyun.post.dashboard.component.RequestLog;
import hyun.post.dashboard.filter.InboundRequestFilter;
import hyun.post.dashboard.filter.TaskTimeFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@RequiredArgsConstructor
public class FilterConfig {
    
    //필터는 사실상 시큐리티로 이관

//    private final RequestLog requestLog;

//    @Bean
//    public FilterRegistrationBean<Filter> taskTimeFilter() {
//        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new TaskTimeFilter());
//        bean.setOrder(1);
//        bean.addUrlPatterns("/*");
//        return bean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<Filter> inboundRequestFilter() {
//        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new InboundRequestFilter(requestLog));
//        bean.setOrder(2);
//        bean.addUrlPatterns("/*");
//        return bean;
//    }
}
