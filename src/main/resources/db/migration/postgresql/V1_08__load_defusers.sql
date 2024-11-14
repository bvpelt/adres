-- users
insert into users (userid, username, password, email, phone, accountNonExpired, accountNonLocked, credentialsNonExpired,
                   enabled)
values (1, 'admin', '12345', 'admin@gmail.com', '0645342321', true, true, true, true);
insert into users (userid, username, password, email, phone, accountNonExpired, accountNonLocked, credentialsNonExpired,
                   enabled)
values (2, 'user', '12345', 'admin@gmail.com', '0645342321', true, true, true, true);
insert into users (userid, username, password, email, phone, accountNonExpired, accountNonLocked, credentialsNonExpired,
                   enabled)
values (3, 'bvpelt', '12345', 'admin@gmail.com', '0645342321', true, true, true, true);

-- roles
insert into role (roleid, rolename, description) values (1, 'ADMIN', 'Administrator');
insert into role (roleid, rolename, description) values (2, 'USER', 'User');

insert into users_roles (id, userid, roleid) values (1, 1, 1);
insert into users_roles (id, userid, roleid) values (2, 2, 2);
insert into users_roles (id, userid, roleid) values (3, 3, 2);
