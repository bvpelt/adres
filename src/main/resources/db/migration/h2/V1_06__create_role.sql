create table roles
(
    id          bigint auto_increment primary key,
    rolename    varchar(32) not null,
    description varchar(128),
    hash        int
);

create unique index role_rolename_ix on roles (rolename);


