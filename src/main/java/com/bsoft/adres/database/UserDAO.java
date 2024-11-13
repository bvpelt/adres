package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.UserBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(mappedBy = "user")
    private List<RoleDAO> role = new ArrayList<RoleDAO>();

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
        this.setRole(new ArrayList<>());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", role=" + role +
                ", hash=" + hash +
                '}';
    }
}
