package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.UserBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
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

    // TODO change to Many to Many
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // owning site
    @JoinTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "userid", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleid", referencedColumnName = "id")
            }
    )
    private Collection<RoleDAO> roles = new HashSet<RoleDAO>();

    @Column(name = "hash")
    private int hash;

    // TODO fix account / crentials in java / database
    public UserDAO(final UserBody userbody) {
        this.setUsername(userbody.getUsername());
        this.setPassword(userbody.getPassword());
        this.setEmail(userbody.getEmail());
        this.setPhone(userbody.getPhone());
        this.setAccount_non_expired();
        this.setAccountNonExpired(userbody.getAccountNonExpired());
        this.setAccountNonLocked(userbody.getAccountNonLocked());
        this.setCredentialsNonExpired(userbody.getCredentialsNonExpired());
        this.setEnabled(userbody.getEnabled());
        this.setRoles(new HashSet<>());
        this.hash = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDAO userDAO = (UserDAO) o;
        return Objects.equals(username, userDAO.username) && Objects.equals(password, userDAO.password) && Objects.equals(email, userDAO.email) && Objects.equals(phone, userDAO.phone) && Objects.equals(accountNonExpired, userDAO.accountNonExpired) && Objects.equals(accountNonLocked, userDAO.accountNonLocked) && Objects.equals(credentialsNonExpired, userDAO.credentialsNonExpired) && Objects.equals(enabled, userDAO.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, phone, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "id=" + id +
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
