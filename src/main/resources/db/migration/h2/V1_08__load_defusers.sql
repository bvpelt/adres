-- users
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked,
                    credentials_non_expired,
                    enabled)
values (1, 'admin', '$2a$10$qdMGbms2pgUKE3igwH.5pOmG7X7PS8g2G5UZDtMVho8MtrzChSBnq', 'admin-01@gmail.com',
        '0645342321', true, true, true, true);
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked,
                    credentials_non_expired,
                    enabled)
values (2, 'user', '$2a$10$0aE7M3rERZG1FM8l5Nqc.O2Dz2IYq712cxu7QczZv6qIieSq/I2am', 'admin-02@gmail.com',
        '0645342321', true, true, true, true);
insert into "user" (id, username, password, email, phone, account_non_expired, account_non_locked,
                    credentials_non_expired,
                    enabled)
values (3, 'bvpelt', '$2a$10$4tsz2Vbz7T1TvVfjOrnxtu6vr058B.SjIhi5KS5uk9Q1mvl7hcora', 'admin-03@gmail.com',
        '0645342321', true, true, true, true);

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
