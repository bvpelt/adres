package com.bsoft.adres.mappers;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.database.JsonNullableMapper;
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


    public abstract Adres map(AdresDAO source);

    public abstract AdresDAO map(Adres source);

}
