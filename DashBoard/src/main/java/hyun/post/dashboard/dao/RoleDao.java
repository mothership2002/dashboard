package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.model.entity.Role;
import hyun.post.dashboard.repository.rdbms.MemberRepository;
import hyun.post.dashboard.repository.rdbms.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleDao {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    // TODO 더좋은 방법 찾아야함.
    @Transactional
    public void deleteById(Long roleId) {
        // role 객체를 찾음
        Role role = roleRepository.findById(roleId).orElseThrow(EntityNotFoundException::new);
        // 그보다 낮은 권한들 찾음.
        List<Role> roleList = roleRepository.findByIdAndPriorityLessThan(roleId, role.getPriority());
        // 그보다 낮은 권한 하나 추출
        Optional<Role> changeRole = Optional.ofNullable(roleList.get(0));
        // 지워질 멤버들 리스트 조회
        List<Member> members = memberRepository.findAllByRoleId(roleId);
        // null 아니면 낮은 role로 변화
        members.forEach(member -> member.setRole(changeRole.get()));
        roleRepository.deleteById(roleId);
        memberRepository.saveAll(members);
    }


    // Default Role values;
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<Role> list = new ArrayList<>();
        list.add(new Role("admin", 0));
        list.add(new Role("manager", 1));
        list.add(new Role("user", 10));
        roleRepository.saveAll(list);
    }
}
