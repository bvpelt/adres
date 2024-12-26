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
@Table(name = "apikey", schema = "public", catalog = "adres")
public class ApiKeyDao {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "apikey")
    private String apikey;

    @Column(name = "inuse")
    private Boolean inuse = false;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ApiKeyDao apiKeyDao)) return false;
        return Objects.equals(apikey, apiKeyDao.apikey) && Objects.equals(inuse, apiKeyDao.inuse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apikey, inuse);
    }

    @Override
    public String toString() {
        return "ApiKeyDao{" +
                "id=" + id +
                ", apikey='" + apikey + '\'' +
                ", inuse=" + inuse +
                '}';
    }
}
