-- privileges
insert into privilege (id, name)
values (1, 'READ');
insert into privilege (id, name)
values (2, 'WRITE');

insert into roles_privileges (id, privilegeid, roleid)
values (1, 1, 2); -- read - user
insert into roles_privileges (id, privilegeid, roleid)
values (2, 1, 1); -- read - admin
insert into roles_privileges (id, privilegeid, roleid)
values (3, 2, 1); -- write - admin

SELECT setval('privilege_id_seq', 2, true); -- next value will be 4

SELECT setval('roles_privileges_id_seq', 3, true); -- next value will be 4