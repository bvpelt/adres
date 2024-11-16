create table roles_privileges
(
    id     bigint not null auto_increment primary key,
    privilegeid bigint,
    roleid bigint
);

create index roles_privileges_userid_ix on roles_privileges (privilegeid);
create index roles_privileges_roleid_ix on roles_privileges (roleid);

