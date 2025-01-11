package com.bsoft.adres.mappers;

import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.User;
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
public abstract class UserMapper implements JsonNullableMapper {

    @Mapping(target = "accountNonExpired", source = "account_non_expired")
    @Mapping(target = "accountNonLocked", source = "account_non_locked")
    @Mapping(target = "credentialsNonExpired", source = "credentials_non_expired")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapToRoles")
    public abstract User map(UserDAO source);

    @Named("mapToRoles")
    public List<Role> roleDAOCollectionToRoleList (Collection<RoleDAO> source) {
        List<Role> roles = new ArrayList<>();

        for (RoleDAO roleDAO : source) {
            Role role = roleDAOToRole01(roleDAO);
            roles.add(role);
        }
        return roles;
    }

    public Role roleDAOToRole01(RoleDAO roleDAO) {
        return Mappers.getMapper(RoleMapper.class).map(roleDAO);
    }

    @Mapping(target = "account_non_expired", source = "accountNonExpired")
    @Mapping(target = "account_non_locked", source = "accountNonLocked")
    @Mapping(target = "credentials_non_expired", source = "credentialsNonExpired")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapToRolesDAO")
    public abstract UserDAO map(User source);

    @Named("mapToRolesDAO")
    public Collection<RoleDAO> roleListToRoleCollection (List<Role> source) {
        Collection<RoleDAO> roles = new ArrayList<>();

        for (Role role : source) {
            RoleDAO roleDAO = roleToRoleDAO01(role);
            roles.add(roleDAO);
        }
        return roles;
    }

    public RoleDAO roleToRoleDAO01(Role role) {
        return Mappers.getMapper(RoleMapper.class).map(role);
    }
}
