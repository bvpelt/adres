-- users
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (1, 'admin', '$2a$10$qdMGbms2pgUKE3igwH.5pOmG7X7PS8g2G5UZDtMVho8MtrzChSBnq', 'admin-01@gmail.com',
        '0645342321', -1, true, true, true, true);
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (2, 'user', '$2a$10$1ToAmBgcT0lSu3BVYsy7Cu3aSc5mabxF9/l1MOR9ngfkR1SpWgbR6', 'admin-02@gmail.com',
        '0612345678', -135942615, true, true, true, true);
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (3, 'bvpelt', '$2a$10$4tsz2Vbz7T1TvVfjOrnxtu6vr058B.SjIhi5KS5uk9Q1mvl7hcora', 'admin-03@gmail.com',
        '0645342321', -135942615, true, true, true, true);

-- roles
insert into role (id, rolename, description)
values (1, 'ADMIN', 'Administrator');
insert into role (id, rolename, description)
values (2, 'USER', 'User');
insert into role (id, rolename, description)
values (3, 'OPERATOR', 'OPERATOR');

insert into users_roles (id, userid, roleid)
values (1, 1, 1);
insert into users_roles (id, userid, roleid)
values (2, 2, 2);
insert into users_roles (id, userid, roleid)
values (3, 3, 3);

SELECT setval('users_id_seq', 3, true); -- next value will be 4
SELECT setval('role_id_seq', 3, true); -- next value will be 4
SELECT setval('users_roles_id_seq', 3, true); -- next value will be 4
