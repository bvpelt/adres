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
insert into roles_privileges (id, privilegeid, roleid)
values (4, 2, 3); -- write - operator

ALTER TABLE privilege ALTER COLUMN id RESTART WITH 3;
ALTER TABLE roles_privileges ALTER COLUMN id RESTART WITH 5;
