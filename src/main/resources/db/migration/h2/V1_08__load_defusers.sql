-- users
insert into user (id, username, password, email, phone, account_non_expired, account_non_locked,
                  credentials_non_expired,
                  enabled, hash)
values (1, 'admin', '12345', 'admin@gmail.com', '0645342321', true, true, true, true, 1);
insert into user (id, username, password, email, phone, account_non_expired, account_non_locked,
                  credentials_non_expired,
                  enabled, hash)
values (2, 'user', '12345', 'admin@gmail.com', '0645342321', true, true, true, true, 1);
insert into user (id, username, password, email, phone, account_non_expired, account_non_locked,
                  credentials_non_expired,
                  enabled, hash)
values (3, 'bvpelt', '12345', 'admin@gmail.com', '0645342321', true, true, true, true, 1);

-- roles
insert into role (id, rolename, description)
values (1, 'ADMIN', 'Administrator');
insert into role (id, rolename, description)
values (2, 'USER', 'User');

insert into user_role (id, userid, roleid)
values (1, 1, 1);
insert into user_role (id, userid, roleid)
values (2, 2, 2);
insert into user_role (id, userid, roleid)
values (3, 3, 2);
