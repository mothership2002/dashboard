package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Role;
import hyun.post.dashboard.repository.rdbms.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleDao {

    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<Role> list = new ArrayList<>();
        list.add(new Role("admin", 0));
        list.add(new Role("manager", 1));
        list.add(new Role("user", 10));
        roleRepository.saveAll(list);
    }
}
