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
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
                                "/auth/login",
                                "/members/add",
                                "/home",
                                "/swagger/**"
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
        ProviderManager manager = (ProviderManager) config.getAuthenticationManager();
        manager.getProviders().add(customAuthenticationProvider());
        return manager;
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(memberService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
