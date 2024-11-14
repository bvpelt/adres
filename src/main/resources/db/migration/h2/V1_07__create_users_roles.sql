create table users_roles
(
    id     bigint not null auto_increment primary key,
    userid bigint,
    roleid bigint
);


create index users_roles_userid_ix on users_roles (userid);
create index users_roles_roleid_ix on users_roles (roleid);

