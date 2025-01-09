package com.bsoft.adres.mappers;

import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.User;
import lombok.Setter;
import org.mapstruct.*;

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
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesDAO")
    public abstract User map(UserDAO source);

    @Named("mapRolesDAO")
    List<Role> mapRolesDAO(Collection<RoleDAO> source) {
        if (source == null) {
            return null;
        }

        List<Role> roles = new ArrayList<>();
        source.forEach(roleDAO -> {
            roles.add(roleDAOToRole(roleDAO));
        });

        return roles;
    }

    private Role roleDAOToRole(RoleDAO source) {
        if (source == null) {
            return null;
        }

        Role role = new Role();
        role.setId(source.getId());
        role.setDescription(source.getDescription());
        role.setRolename(source.getRolename());

        return role;
    }

    @Mapping(target = "account_non_expired", source = "accountNonExpired")
    @Mapping(target = "account_non_locked", source = "accountNonLocked")
    @Mapping(target = "credentials_non_expired", source = "credentialsNonExpired")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoles")
    public abstract UserDAO map(User source);

    @Named("mapRoles")
    List<RoleDAO> mapRoles(Collection<Role> source) {
        if (source == null) {
            return null;
        }

        List<RoleDAO> roles = new ArrayList<>();
        source.forEach(role -> {
            roles.add(roleToRoleDAO(role));
        });

        return roles;
    }

    private RoleDAO roleToRoleDAO(Role source) {
        if (source == null) {
            return null;
        }

        RoleDAO role = new RoleDAO();
        role.setId(source.getId());
        role.setDescription(source.getDescription());
        role.setRolename(source.getRolename());

        return role;
    }
}
