package com.bsoft.adres.mappers;

import com.bsoft.adres.database.JsonNullableMapper;
import com.bsoft.adres.database.RolesDTO;
import com.bsoft.adres.database.UserDTO;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.User;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                JsonNullableMapper.class
        },
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class UserMapper implements JsonNullableMapper {

    @Autowired  // Inject the RoleMapper
    private RoleMapper roleMapper;

    @Mapping(target = "accountNonExpired", source = "account_non_expired")
    @Mapping(target = "accountNonLocked", source = "account_non_locked")
    @Mapping(target = "credentialsNonExpired", source = "credentials_non_expired")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapToRoles")
    public abstract User map(UserDTO source);

    @Named("mapToRoles")
    public List<Role> roleDTOCollectionToRoleList(Collection<RolesDTO> source) {
        /*
        List<Role> roles = new ArrayList<>();

        for (RolesDTO rolesDTO : source) {
            Role role = roleDTOToRole(rolesDTO);
            roles.add(role);
        }
        return roles;

         */
        return source.stream()
                .map(roleMapper::map) // Use the injected RoleMapper
                .collect(Collectors.toList());
    }

    /*
    public Role roleDTOToRole(RolesDTO rolesDTO) {
        return Mappers
                .getMapper(RoleMapper.class)
                .map(rolesDTO);
    }

     */

    @Mapping(target = "account_non_expired", source = "accountNonExpired")
    @Mapping(target = "account_non_locked", source = "accountNonLocked")
    @Mapping(target = "credentials_non_expired", source = "credentialsNonExpired")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapToRolesDTO")
    public abstract UserDTO map(User source);

    @Named("mapToRolesDTO")
    public Collection<RolesDTO> roleListToRoleCollection(List<Role> source) {
        /*
        Collection<RolesDTO> roles = new ArrayList<>();

        for (Role role : source) {
            RolesDTO rolesDTO = roleToRoleDTO(role);
            roles.add(rolesDTO);
        }
        return roles;
         */

        return source.stream()
                .map(roleMapper::map) // Use the injected RoleMapper
                .collect(Collectors.toList());
    }
/*
    public RolesDTO roleToRoleDTO(Role role) {
        return Mappers
                .getMapper(RoleMapper.class)
                .map(role);
    }

 */
}
