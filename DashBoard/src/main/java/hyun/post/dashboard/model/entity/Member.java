package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "MEMBER", indexes = {
        @Index(name = "MEMBER_ROLE", columnList = "ROLE_ID"),
        @Index(name = "MEMBER_ACCOUNT", columnList = "ACCOUNT"),
        @Index(name = "MEMBER_EMAIL", columnList = "EMAIL")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL ")
//@SQLDelete(sql = "UPDATE MEMBER " +
//        "SET DELETED_AT = current_timestamp " +
//        "WHERE MEMBER_ID = ? ")
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;


    public Member(String account, String password, String email) {
        this.account = account;
        this.password = password;
        this.email = email;
    }

    // TODO 권한 관련 공부 필요
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
