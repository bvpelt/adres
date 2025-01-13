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

insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (4, 'fber', '$2a$10$Z6pq4.8lVz8Tr4.fmZXXAOYPoyxvYA4mzu0sXiTWgoN9VVQriWfoC', 'fber@gmail.com',
        '0658281209', -1604980512, true, true, true, true);

insert into users (id, username, password, email, phone, hash, account_non_expired, account_non_locked,
                   credentials_non_expired,
                   enabled)
values (5, 'develop', '$2a$10$Z6pq4.8lVz8Tr4.fmZXXAOYPoyxvYA4mzu0sXiTWgoN9VVQriWfoC', 'develop@gmail.com',
        '0656781209', -1604980512, true, true, true, true);

-- user roles
insert into users_roles (id, userid, roleid)
values (1, 1, 1); -- admin ADMIN
insert into users_roles (id, userid, roleid)
values (2, 3, 2); -- bvpelt FUNC_OPERATOR
insert into users_roles (id, userid, roleid)
values (3, 3, 3); -- bvpelt TECH_OPERATOR
insert into users_roles (id, userid, roleid)
values (4, 4, 2); -- fber FUNC_OPERATOR
insert into users_roles (id, userid, roleid)
values (5, 2, 4); -- user USER
insert into users_roles (id, userid, roleid)
values (6, 5, 6); -- develop DEVELOPER

ALTER TABLE users
    ALTER COLUMN id RESTART WITH 6;
ALTER TABLE users_roles
    ALTER COLUMN id RESTART WITH 7;

update users
set apikey_id = 1
where username = 'bvpelt';
update users
set apikey_id = 2
where username = 'admin';
update users
set apikey_id = 3
where username = 'user';
