package com.bsoft.adres.database;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role", schema = "public", catalog = "adres")
public class RoleDAO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Long roleid;

    @Column(name = "rolename")
    private String rolename;

    @Column(name = "description")
    private String description;

    @Column(name = "hash")
    private int hash;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<UserDAO> users = new HashSet<UserDAO>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleDAO roleDAO)) return false;

        if (getRolename() != null ? !getRolename().equals(roleDAO.getRolename()) : roleDAO.getRolename() != null)
            return false;
        return getDescription() != null ? getDescription().equals(roleDAO.getDescription()) : roleDAO.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getRolename() != null ? getRolename().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleDAO{" +
                "roleid=" + roleid +
                ", rolename='" + rolename + '\'' +
                ", description='" + description + '\'' +
                ", hash=" + hash +
                ", users=" + users +
                '}';
    }
}
