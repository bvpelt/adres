package com.bsoft.adres.mappers;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.PersonDAO;
import com.bsoft.adres.database.RolesDAO;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.Role;
import lombok.Setter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                JsonNullableMapper.class
        },
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class AdresMapper implements JsonNullableMapper {

    @Mapping(source = "persons", target = "persons", qualifiedByName = "mapToPersons")
    public abstract Adres map(AdresDAO source);

    @Named("mapToPersons")
    public List<Person> personDAOCollectionToPersonList(Collection<PersonDAO> source) {
        List<Person> persons = new ArrayList<>();

        for (PersonDAO personDAO : source) {
            Person person = personDAOToPerson(personDAO);
            persons.add(person);
        }
        return persons;
    }

    public Person personDAOToPerson(PersonDAO personDAO) {
        return Mappers.getMapper(PersonMapper.class).map(personDAO);
    }

    @Mapping(source = "persons", target = "persons", qualifiedByName = "mapToPersonsDAO")
    public abstract AdresDAO map(Adres source);

    @Named("mapToPersonsDAO")
    public Collection<PersonDAO> personListToPersonCollection(List<Person> source) {
        Collection<PersonDAO> persons = new ArrayList<>();

        for (Person person : source) {
            PersonDAO personDAO = roleToRoleDAO(person);
            persons.add(personDAO);
        }
        return persons;
    }

    public PersonDAO roleToRoleDAO(Person person) {
        return Mappers.getMapper(PersonMapper.class).map(person);
    }

}
