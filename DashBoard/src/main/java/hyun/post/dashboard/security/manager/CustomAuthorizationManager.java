package hyun.post.dashboard.security.manager;

import hyun.post.dashboard.exception.AllowAllMethodException;
import hyun.post.dashboard.model.entity.Resource;
import hyun.post.dashboard.model.entity.Role;
import hyun.post.dashboard.model.relation.RoleAndResource;
import hyun.post.dashboard.repository.rdbms.ResourceRepository;
import hyun.post.dashboard.repository.rdbms.RoleAndResourceRepository;
import hyun.post.dashboard.repository.rdbms.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
    private static final AuthorizationDecision PERMIT = new AuthorizationDecision(true);
    private final RoleAndResourceRepository roleAndResourceRepository;
    private final Map<String, List<RequestMatcher>> resourceMap = new HashMap<>();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
        // 2차원배열로 포문이 돌아가는 단점 있음. -> 시간복잡도 증가
        // 리소시스 관련으로 롤의 역할을 줄여놓음 -> 1차원배열로 낮춤
        for (GrantedAuthority auth : authorities) {
            for (RequestMatcher matcher : resourceMap.get(auth.getAuthority())) {
                if (matcher.matcher(context.getRequest()).isMatch()) {
                    return PERMIT;
                }
            }
        }
        return DENY;
    }

    // 이벤트로 관리가 가능하지 않을까?
    @EventListener(ApplicationReadyEvent.class)
    public void synchronizeAuthorization() {
        List<RoleAndResource> allWithJoin = roleAndResourceRepository.findAllWithJoin();
        resourceMap.clear();
        for (RoleAndResource roleAndResource : allWithJoin) {
            Role role = roleAndResource.getRole();
            Resource resource = roleAndResource.getResource();
            MvcRequestMatcher.Builder builder = new MvcRequestMatcher.Builder(new HandlerMappingIntrospector());
            try {
                MvcRequestMatcher reqMatcher = builder
                        .pattern(resource.getMethod().convert(), resource.getUrl());
                resourceMap.computeIfAbsent(role.getRoleName(), k -> new ArrayList<>()).add(reqMatcher);

            } catch (AllowAllMethodException e) {
                List<RequestMatcher> collect = Arrays.stream(HttpMethod.values())
                        .map(method -> builder.pattern(method, resource.getUrl()))
                        .collect(Collectors.toList());
                resourceMap.put(role.getRoleName(), collect);
            }
        }
    }

}
