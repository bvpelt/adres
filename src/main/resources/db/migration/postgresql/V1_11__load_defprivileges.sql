-- privileges
insert into privilege (id, name, hash)
values (1, 'ALL', 64897 );
insert into privilege (id, name, hash)
values (2, 'APP_FULL_ACCESS', 1943814186);
insert into privilege (id, name, hash)
values (3, 'APP_SECURITY_ACCESS', 1084503803);
insert into privilege (id, name, hash)
values (4, 'APP_READ_ACCESS_BASIC', 233304766);
insert into privilege (id, name, hash)
values (5, 'APP_READ_ACCESS_JWT', 312597673);

-- Connect to roles
insert into roles_privileges (id, privilegeid, roleid)
values (1, 1, 1); -- ALL ADMIN
insert into roles_privileges (id, privilegeid, roleid)
values (2, 2, 2); -- APP_FULL_ACCESS OPERATOR
insert into roles_privileges (id, privilegeid, roleid)
values (3, 3, 2); -- APP_SECURITY_ACCESS OPERATOR
insert into roles_privileges (id, privilegeid, roleid)
values (4, 4, 3); -- APP_READ_ACCESS_BASIC USER
insert into roles_privileges (id, privilegeid, roleid)
values (5, 5, 4); -- APP_READ_ACCESS_JWT JWT-TOKEN

SELECT setval('privilege_id_seq', 5, true); -- next value will be 6

SELECT setval('roles_privileges_id_seq', 5, true); -- next value will be 6