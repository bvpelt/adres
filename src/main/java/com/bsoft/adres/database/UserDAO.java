package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.UserBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "public", catalog = "adres")
public class UserDAO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "accountNonExpired")
    private Boolean accountNonExpired = true;

    @Column(name = "accountNonLocked")
    private Boolean accountNonLocked = true;

    @Column(name = "credentialsNonExpired")
    private Boolean credentialsNonExpired = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    // TODO change to Many to Many
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // owning site
    @JoinTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "userid", referencedColumnName = "userid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleid", referencedColumnName = "roleid")
            }
    )
    private Set<RoleDAO> roles = new HashSet<RoleDAO>();

    @Column(name = "hash")
    private int hash;

    public UserDAO(final UserBody userbody) {
        this.setUsername(userbody.getUsername());
        this.setPassword(userbody.getPassword());
        this.setEmail(userbody.getEmail());
        this.setPhone(userbody.getPhone());
        this.setAccountNonExpired(userbody.getAccountNonExpired());
        this.setAccountNonLocked(userbody.getAccountNonLocked());
        this.setCredentialsNonExpired(userbody.getCredentialsNonExpired());
        this.setEnabled(userbody.getEnabled());
        this.setRoles(new HashSet<>());
        this.hash = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDAO userDAO)) return false;

        if (getUsername() != null ? !getUsername().equals(userDAO.getUsername()) : userDAO.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(userDAO.getPassword()) : userDAO.getPassword() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(userDAO.getEmail()) : userDAO.getEmail() != null) return false;
        if (getPhone() != null ? !getPhone().equals(userDAO.getPhone()) : userDAO.getPhone() != null) return false;
        if (getAccountNonExpired() != null ? !getAccountNonExpired().equals(userDAO.getAccountNonExpired()) : userDAO.getAccountNonExpired() != null)
            return false;
        if (getAccountNonLocked() != null ? !getAccountNonLocked().equals(userDAO.getAccountNonLocked()) : userDAO.getAccountNonLocked() != null)
            return false;
        if (getCredentialsNonExpired() != null ? !getCredentialsNonExpired().equals(userDAO.getCredentialsNonExpired()) : userDAO.getCredentialsNonExpired() != null)
            return false;
        return getEnabled() != null ? getEnabled().equals(userDAO.getEnabled()) : userDAO.getEnabled() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAccountNonExpired() != null ? getAccountNonExpired().hashCode() : 0);
        result = 31 * result + (getAccountNonLocked() != null ? getAccountNonLocked().hashCode() : 0);
        result = 31 * result + (getCredentialsNonExpired() != null ? getCredentialsNonExpired().hashCode() : 0);
        result = 31 * result + (getEnabled() != null ? getEnabled().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", hash=" + hash +
                '}';
    }
}
