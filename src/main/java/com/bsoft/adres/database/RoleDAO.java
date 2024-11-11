package com.bsoft.adres.database;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

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
}
