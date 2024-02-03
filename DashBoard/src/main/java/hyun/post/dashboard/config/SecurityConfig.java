package hyun.post.dashboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.security.encrypt.EncryptionProvider;
import hyun.post.dashboard.security.filter.AuthenticationLoginFilter;
import hyun.post.dashboard.security.handler.*;
import hyun.post.dashboard.security.provider.CustomAuthenticationProvider;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration config;
    private final CustomFailureHandler failureHandler;
    private final CustomSuccessHandler successHandler;
    private final EncryptionProvider encryptionProvider;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final CommonRespHeaderComponent headerComponent;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler
                                .accessDeniedHandler(customAccessDeniedHandler())
                                .authenticationEntryPoint(customEntryPoint())
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
//                                "/v1/auth/login",
//                                "/v1/member/add",
//                                "/home",
//                                "/swagger/**"
                                "/**"  //TODO Temporary
                        ).permitAll())
                .addFilterBefore(authenticationLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
        //TODO 각종 필터 달아야함
    }

    @Bean
    public AuthenticationLoginFilter authenticationLoginFilter() throws Exception {
        return new AuthenticationLoginFilter(
                authenticationManager(),
                successHandler,
                failureHandler,
                objectMapper,
                encryptionProvider);
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(memberService, passwordEncoder);
    }

    @Bean
    public AuthenticationEntryPoint customEntryPoint() {
        return new CustomEntryPoint(objectMapper, headerComponent);
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler(objectMapper, headerComponent);
    }

}
