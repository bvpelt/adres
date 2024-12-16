package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.UserBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "public", catalog = "adres")
public class UserDAO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "account_non_expired")
    private Boolean account_non_expired = true;

    @Column(name = "account_non_locked")
    private Boolean account_non_locked = true;

    @Column(name = "credentials_non_expired")
    private Boolean credentials_non_expired = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "hash")
    private Integer hash = -1;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // owning site
    @JoinTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "userid", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleid", referencedColumnName = "id")
            }
    )
    private Collection<RoleDAO> roles = new ArrayList<>();

    public void genHash() {
        this.hash = hashCode();
    }

    public UserDAO(final UserBody userbody) {
        this.setUsername(userbody.getUsername());
        this.setPassword(userbody.getPassword());
        this.setEmail(userbody.getEmail());
        this.setPhone(userbody.getPhone());
        this.setAccount_non_expired(userbody.getAccountNonExpired());
        this.setAccount_non_expired(userbody.getAccountNonExpired());
        this.setAccount_non_locked(userbody.getAccountNonLocked());
        this.setCredentials_non_expired(userbody.getCredentialsNonExpired());
        this.setEnabled(userbody.getEnabled());
        this.setRoles(new HashSet<>());
        this.hash = hashCode();
    }

    public void addRole(RoleDAO role) {
        roles.add(role);
    }

    public void setRoles(Collection<RoleDAO> roles) {
        roles.forEach(role -> {
            role.addUser(this);
            this.roles.add(role);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDAO userDAO = (UserDAO) o;
        return Objects.equals(username, userDAO.username) && Objects.equals(password, userDAO.password) && Objects.equals(email, userDAO.email) && Objects.equals(phone, userDAO.phone) && Objects.equals(account_non_expired, userDAO.account_non_expired) && Objects.equals(account_non_locked, userDAO.account_non_locked) && Objects.equals(credentials_non_expired, userDAO.credentials_non_expired) && Objects.equals(enabled, userDAO.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, phone, account_non_expired, account_non_locked, credentials_non_expired, enabled);
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accountNonExpired=" + account_non_expired +
                ", accountNonLocked=" + account_non_locked +
                ", credentialsNonExpired=" + credentials_non_expired +
                ", enabled=" + enabled +
                // ", roles=" + roles +
                ", hash=" + hash +
                '}';
    }
}
