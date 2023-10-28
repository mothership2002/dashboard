package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
public class Member extends BaseDateColumn implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    @Comment("식별자")
    private Long id;

    @Column(name = "ACCOUNT", nullable = false, unique = true)
    @Comment("계정명")
    private String account;

    @Column(name = "PASSWORD", nullable = false)
    @Comment("패스워드")
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Comment("이메일")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;


    public Member(String account, String password, String email) {
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // TODO 권환 관련 공부 필요
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<Role> objects = new HashSet<>();
        objects.add(role);
        return objects;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
