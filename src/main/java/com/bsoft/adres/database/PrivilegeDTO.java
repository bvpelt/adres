package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.PrivilegeBody;
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
public class PrivilegeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hash")
    private Integer hash = -1;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RolesDTO> roles = new ArrayList<>();

    public PrivilegeDTO(PrivilegeBody privilegeBody) {
        this.name = privilegeBody.getName();
        this.hash = this.genHash();
    }

    public void addRole(RolesDTO role) {
        roles.add(role);
    }

    public Integer genHash() {
        this.hash = hashCode();
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PrivilegeDTO that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "PrivilegeDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hash=" + hash +
//                ", roles=" + roles +
                '}';
    }
}
