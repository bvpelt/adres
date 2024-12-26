create table users_roles
(
    id     bigint auto_increment primary key,
    userid bigint,
    roleid bigint
);


create unique index users_roles_userid_ix on users_roles (userid, roleid);
