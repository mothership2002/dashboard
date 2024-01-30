package hyun.post.dashboard.config;

import hyun.post.dashboard.filter.InboundRequestFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> inboundRequestFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new InboundRequestFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }
}
