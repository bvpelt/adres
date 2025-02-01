package com.bsoft.adres.mappers;

import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.RolesDTO;
import com.bsoft.adres.generated.model.Role;
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
public abstract class RoleMapper implements JsonNullableMapper {

    public abstract Role map(RolesDTO source);

    public abstract RolesDTO map(Role source);

}
