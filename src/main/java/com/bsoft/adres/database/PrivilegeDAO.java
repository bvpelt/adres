package com.bsoft.adres.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "privilege", schema = "public", catalog = "adres")
public class PrivilegeDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hash")
    private Integer hash = -1;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleDAO> roles = new ArrayList<>();

    public void addRole(RoleDAO role) {
        roles.add(role);
    }

    public void genHash() {
        this.hash = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PrivilegeDAO privilegeDAO = (PrivilegeDAO) o;
        return Objects.equals(name, privilegeDAO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hash=" + hash +
//                ", roles=" + roles +
                '}';
    }

}
