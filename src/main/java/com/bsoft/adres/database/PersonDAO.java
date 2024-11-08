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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person", schema = "public", catalog = "adres")
public class PersonDAO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personid")
    private Long personid;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "infix")
    private String infix;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "hash")
    private Integer hash;
    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;

    public PersonDAO(final Person person) {
        this.personid = person.getPersonId();
        this.firstname = person.getFirstName();
        this.infix = person.getInfix();
        this.lastname = person.getLastName();
        this.dateofbirth = person.getDateOfBirth();
        this.hash = this.hashCode();
    }

    public PersonDAO(final PersonBody person) {
        this.personid = null;
        this.firstname = person.getFirstName();
        this.infix = person.getInfix();
        this.lastname = person.getLastName();
        this.dateofbirth = person.getDateOfBirth();
        this.hash = this.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDAO personDAO)) return false;

        if (getFirstname() != null ? !getFirstname().equals(personDAO.getFirstname()) : personDAO.getFirstname() != null)
            return false;
        if (getInfix() != null ? !getInfix().equals(personDAO.getInfix()) : personDAO.getInfix() != null) return false;
        if (getLastname() != null ? !getLastname().equals(personDAO.getLastname()) : personDAO.getLastname() != null)
            return false;
        return getDateofbirth() != null ? getDateofbirth().equals(personDAO.getDateofbirth()) : personDAO.getDateofbirth() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstname() != null ? getFirstname().hashCode() : 0;
        result = 31 * result + (getInfix() != null ? getInfix().hashCode() : 0);
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getDateofbirth() != null ? getDateofbirth().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDAO{" +
                "personid=" + personid +
                ", firstname='" + firstname + '\'' +
                ", infix='" + infix + '\'' +
                ", lastname='" + lastname + '\'' +
                ", hash=" + hash +
                ", dateofbirth=" + dateofbirth +
                '}';
    }
}
