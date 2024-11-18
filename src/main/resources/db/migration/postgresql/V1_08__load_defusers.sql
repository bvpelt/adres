-- users
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked, credentials_non_expired,
                   enabled)
values (1, 'admin', '$2a$10$oeT7CVLFe/zg1v7P8o7CBejz1zb5nbuLb/lXwCRanV/awMmGGwELC', 'admin-01@gmail.com', '0645342321', true, true, true, true);
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked, credentials_non_expired,
                   enabled)
values (2, 'user', '$2a$10$Clxb3Pb3EwgZYAfXOPy7bOlGcufDl.EdFM.EyQM0.dyrBT3kyitcq', 'admin-02@gmail.com', '0645342321', true, true, true, true);
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked, credentials_non_expired,
                   enabled)
values (3, 'bvpelt', '$2a$10$FHPiHRV5Ukg8hXzZnEW2bunJvRxAFnE.ssSrRgDWAAiNStjINms1K', 'admin-03@gmail.com', '0645342321', true, true, true, true);

-- roles
insert into role (id, rolename, description) values (1, 'ADMIN', 'Administrator');
insert into role (id, rolename, description) values (2, 'USER', 'User');

insert into users_roles (id, userid, roleid) values (1, 1, 1);
insert into users_roles (id, userid, roleid) values (2, 2, 2);
insert into users_roles (id, userid, roleid) values (3, 3, 2);

SELECT setval('user_id_seq', 3, true);  -- next value will be 4
SELECT setval('role_id_seq', 2, true);  -- next value will be 3
SELECT setval('users_roles_id_seq', 3, true);  -- next value will be 4