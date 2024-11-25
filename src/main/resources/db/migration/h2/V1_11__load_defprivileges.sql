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