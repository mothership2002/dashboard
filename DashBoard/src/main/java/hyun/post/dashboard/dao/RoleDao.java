package hyun.post.dashboard.dao;

import hyun.post.dashboard.exception.entity.NotFoundRoleException;
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

    // 권한 자체는 삭제 할 이유가 없음.
//    @Transactional
//    public void deleteById(Long roleId) {
//        Role role = roleRepository.findById(roleId).orElseThrow(NotFoundRoleException::new);
//        List<Role> roleList = roleRepository.findByIdAndPriorityLessThan(roleId, role.getPriority());
//        Optional<Role> changeRole = Optional.ofNullable(roleList.get(0));
//        List<Member> members = memberRepository.findAllByRoleId(roleId);
//        members.forEach(member -> member.setRole(changeRole.orElseThrow(NotFoundRoleException::new)));
//        roleRepository.deleteById(roleId);
//        memberRepository.saveAll(members);
//    }

    @Transactional(readOnly = true)
    public Role findOneByRoleName(String roleName) {
        return roleRepository.findOneByRoleName(roleName).orElseThrow(NotFoundRoleException::new);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
