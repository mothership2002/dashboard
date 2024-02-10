package hyun.post.dashboard.listener;

import hyun.post.dashboard.model.common.HttpMethod;
import hyun.post.dashboard.model.entity.Resource;
import hyun.post.dashboard.model.entity.Role;
import hyun.post.dashboard.model.relation.RoleAndResource;
import hyun.post.dashboard.repository.rdbms.ResourceRepository;
import hyun.post.dashboard.repository.rdbms.RoleAndResourceRepository;
import hyun.post.dashboard.repository.rdbms.RoleRepository;
import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.LoginSessionRepository;
import hyun.post.dashboard.repository.redis.LoginTransactionRepository;
import hyun.post.dashboard.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitializer {

    private final RoleRepository roleRepository;
    private final RoleAndResourceRepository roleAndResourceRepository;
    private final ResourceRepository resourceRepository;

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginSessionRepository loginSessionRepository;
    private final LoginTransactionRepository loginTransactionRepository;

    // Default Role values;
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Application init Start");
        List<Role> roles = initDefaultRole();
        List<Resource> resources = initDefaultResource();
        initAdmin();
        initDefaultAuthorization(roles, resources);
        initializedRedis();
        log.info("Application init End");
    }

    private void initAdmin() {
        Role admin = new Role("admin", 0);
        Resource resource = new Resource("/**", HttpMethod.ALL);
        RoleAndResource roleAndResource = new RoleAndResource(admin, resource);
        roleRepository.save(admin);
        resourceRepository.save(resource);
        roleAndResourceRepository.save(roleAndResource);
    }

    private List<Role> initDefaultRole() {
        List<Role> list = new ArrayList<>();
        list.add(new Role("manager", 1));
        list.add(new Role("user", 10));
        roleRepository.saveAll(list);
        return list;
    }

    private List<Resource> initDefaultResource() {
        List<Resource> list = new ArrayList<>();
        list.add(new Resource("/v1/**", HttpMethod.GET));         // default
        list.add(new Resource("/swagger/**", HttpMethod.GET));    // dev
        resourceRepository.saveAll(list);
        return list;
    }

    private void initDefaultAuthorization(List<Role> roles, List<Resource> resources) {
//        Map<String, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getRoleName, Function.identity()));
        List<RoleAndResource> roleAndResources = new ArrayList<>();
        for (Role role : roles) {
            for (Resource resource : resources) {
                RoleAndResource roleAndResource = new RoleAndResource(role, resource);
                roleAndResources.add(roleAndResource);
            }
        }
        roleAndResourceRepository.saveAll(roleAndResources);
    }

    private void initializedRedis() {
        accessTokenRepository.deleteAll();
        refreshTokenRepository.deleteAll();
        loginTransactionRepository.deleteAll();
        loginSessionRepository.deleteAll();
    }
}
