package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.PersonBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person", schema = "public", catalog = "adres")
public class PersonDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "infix")
    private String infix;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;
    @Column(name = "hash")
    private Integer hash;

    @ManyToMany(mappedBy = "persons") //, fetch = FetchType.LAZY)
    private Collection<AdresDTO> adresses = new ArrayList<>();

    public PersonDTO(final Person person) {
        this.id = person.getId();
        this.firstname = person.getFirstName();
        this.infix = person.getInfix();
        this.lastname = person.getLastName();
        this.dateofbirth = person.getDateOfBirth();
        this.hash = this.hashCode();
    }

    public PersonDTO(final PersonBody person) {
        this.id = null;
        this.firstname = person.getFirstName();
        this.infix = person.getInfix();
        this.lastname = person.getLastName();
        this.dateofbirth = person.getDateOfBirth();
        this.hash = this.hashCode();
    }

    public Integer genHash() {
        this.hash = hashCode();
        return this.hash;
    }

    public void addAdres(AdresDTO adres) {
        adresses.add(adres);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(firstname, personDTO.firstname) && Objects.equals(infix, personDTO.infix) && Objects.equals(lastname, personDTO.lastname) && Objects.equals(dateofbirth, personDTO.dateofbirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, infix, lastname, dateofbirth);
    }

    @Override
    public String toString() {
        return "PersonDAO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", infix='" + infix + '\'' +
                ", lastname='" + lastname + '\'' +
                ", hash=" + hash +
                ", dateofbirth=" + dateofbirth +
                '}';
    }
}
