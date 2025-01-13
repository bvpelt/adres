-- privileges
insert into privilege (id, name, hash)
values (1, 'ALL', 64897);
insert into privilege (id, name, hash)
values (2, 'APP_MAINTENANCE', 1562765749);
insert into privilege (id, name, hash)
values (3, 'APP_WRITE', 1253932033);
insert into privilege (id, name, hash)
values (4, 'APP_READ', 1979950356);
insert into privilege (id, name, hash)
values (5, 'APP_READ_JWT', -756069636);

SELECT setval('privilege_id_seq', 5, true); -- next value will be 6
