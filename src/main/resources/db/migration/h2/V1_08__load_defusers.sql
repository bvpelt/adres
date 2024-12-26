-- users
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (1, 'admin', '$2a$10$PtjC/PwF560dOI4QF0R.wOicvv.zMe/R.oZDkfeJ83fNXWcX4MB7C', 'admin@gmail.com',
        '0612345678', -1207799837, true, true, true, true);
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (2, 'user', '$2a$10$rFzxJIP.zO0ILkerUQdDcu8O.nnv.H2QPh1KT7dhr5RDoz15rlxjC', 'user@gmail.com',
        '0678901234', 668774705, true, true, true, true);
insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (3, 'bvpelt', '$2a$10$Z6pq4.8lVz8Tr4.fmZXXAOYPoyxvYA4mzu0sXiTWgoN9VVQriWfoC', 'bvpelt@gmail.com',
        '0656789012', -1604980512, true, true, true, true);

-- roles
insert into role (id, rolename, description, hash) values (1, 'ADMIN',     'Administrator',                              2072793375);
insert into role (id, rolename, description, hash) values (2, 'OPERATOR',  'Operator',                                   -281572255);
insert into role (id, rolename, description, hash) values (3, 'USER',      'Application user with basic authentication', 439955957);
insert into role (id, rolename, description, hash) values (4, 'JWT-TOKEN', 'Application user with JWT token',            -1051810471);

insert into users_roles (id, userid, roleid) values (1, 1, 1);  -- admin ADMIN
insert into users_roles (id, userid, roleid) values (2, 3, 2); -- bvpelt OPERATOR
insert into users_roles (id, userid, roleid) values (3, 2, 3); -- user USER

ALTER TABLE users
    ALTER COLUMN id RESTART WITH 4;
ALTER TABLE role
    ALTER COLUMN id RESTART WITH 5;
ALTER TABLE users_roles
    ALTER COLUMN id RESTART WITH 4;

