package com.bsoft.adres.mappers;

import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.PersonDAO;
import com.bsoft.adres.generated.model.Person;
import lombok.Setter;
import org.mapstruct.*;

@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                JsonNullableMapper.class
        },
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class PersonMapper implements JsonNullableMapper {

    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    @Mapping(target = "dateOfBirth", source = "dateofbirth")
    public abstract Person map(PersonDAO source);

    @Mapping(target = "firstname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    @Mapping(target = "dateofbirth", source = "dateOfBirth")
    public abstract PersonDAO map(Person source);

}
