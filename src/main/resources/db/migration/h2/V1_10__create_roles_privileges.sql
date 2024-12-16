create table roles_privileges
(
    id          bigint not null primary key,
    privilegeid bigint,
    roleid      bigint
);

create unique index roles_privileges_userid_ix on roles_privileges (roleid, privilegeid);
