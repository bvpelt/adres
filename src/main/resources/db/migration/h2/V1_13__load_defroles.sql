-- roles
insert into role (id, rolename, description, hash)
values (1, 'ADMIN', 'Administrator', 2072793375);
insert into role (id, rolename, description, hash)
values (2, 'FUNC_OPERATOR', 'Functional Operator', -281572255);
insert into role (id, rolename, description, hash)
values (3, 'TECH_OPERATOR', 'Technical Operator', 439955957);
insert into role (id, rolename, description, hash)
values (4, 'USER', 'Application user with read access', -1051810471);
insert into role (id, rolename, description, hash)
values (5, 'JWT-TOKEN', 'Registered user with read access', -1051810471);
insert into role (id, rolename, description, hash)
values (6, 'DEVELOPER', 'Developer with all privileges', -1051810471);


-- Connect to roles
insert into roles_privileges (id, roleid, privilegeid)
values (1, 1, 1); -- ADMIN ALL
insert into roles_privileges (id, roleid, privilegeid)
values (2, 2, 3); -- FUNC_OPERATOR APP_WRITE
insert into roles_privileges (id, roleid, privilegeid)
values (3, 3, 2); -- TECH_OPERATOR APP_MAINTENANCE
insert into roles_privileges (id, roleid, privilegeid)
values (4, 4, 4); -- USER APP_READ
insert into roles_privileges (id, roleid, privilegeid)
values (5, 5, 5); -- JWT-TOKEN APP_READ_JWT
insert into roles_privileges (id, roleid, privilegeid)
values (6, 6, 1); -- DEVELOPER ALL

ALTER TABLE role
    ALTER COLUMN id RESTART WITH 7;
ALTER TABLE roles_privileges
    ALTER COLUMN id RESTART WITH 7;
