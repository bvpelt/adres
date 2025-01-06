package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.Adres;
import lombok.Setter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                JsonNullableMapper.class
        },
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class AdresMapper implements JsonNullableMapper {


    //@Mapping(ignore=true, target="hash")
    public abstract Adres map(AdresDAO target);


    //@Mapping(ignore=true, target="hash")
    public abstract AdresDAO map(Adres target);

    /*
    // Optional: Map collections
    List<Adres> mapListFromDAO(List<AdresDAO> source);
    List<AdresDAO> mapListToDAO(List<Adres> target);

     */
}
