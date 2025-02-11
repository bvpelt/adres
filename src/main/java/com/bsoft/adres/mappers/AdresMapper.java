package com.bsoft.adres.mappers;

import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.PersonDTO;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.Person;
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
    public abstract Adres map(AdresDTO source);

    @Named("mapToPersons")
    public List<Person> personDTOCollectionToPersonList(Collection<PersonDTO> source) {
        List<Person> persons = new ArrayList<>();

        for (PersonDTO personDTO : source) {
            Person person = personDTOToPerson(personDTO);
            persons.add(person);
        }
        return persons;
    }

    public Person personDTOToPerson(PersonDTO personDTO) {
        return Mappers.getMapper(PersonMapper.class).map(personDTO);
    }

    @Mapping(source = "persons", target = "persons", qualifiedByName = "mapToPersonsDTO")
    public abstract AdresDTO map(Adres source);

    @Named("mapToPersonsDTO")
    public Collection<PersonDTO> personListToPersonCollection(List<Person> source) {
        Collection<PersonDTO> persons = new ArrayList<>();

        for (Person person : source) {
            PersonDTO personDTO = personToPersonDTO(person);
            persons.add(personDTO);
        }
        return persons;
    }

    public PersonDTO personToPersonDTO(Person person) {
        return Mappers.getMapper(PersonMapper.class).map(person);
    }

}
