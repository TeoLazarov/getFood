package teodorlazarov.getfood.domain.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity implements GrantedAuthority {

    private String role;

    public UserRole() {
    }

    @Column(name = "role", nullable = false, unique = true)
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    @Transient
    public String getAuthority() {
        return this.getRole();
    }
}
