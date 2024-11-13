package com.bsoft.adres.database;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserDAO user;

    @Column(name = "hash")
    private int hash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDAO roleDAO = (RoleDAO) o;
        return Objects.equals(rolename, roleDAO.rolename) && Objects.equals(description, roleDAO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolename, description);
    }

    @Override
    public String toString() {
        return rolename;
    }
}
