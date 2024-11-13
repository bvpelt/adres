create table role
(
    roleid      bigint      not null auto_increment primary key,
    rolename    varchar(32) not null,
    description varchar(128),
    userid      bigint,
    hash        int
);

create index role_rolename_ix on role (rolename);
create index role_userid_ix on role (userid);

