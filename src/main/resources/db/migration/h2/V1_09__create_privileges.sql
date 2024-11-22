create table privilege
(
    id   bigint not null auto_increment primary key,
    name varchar(24),
    hash int
);

create unique index privilege_name_ix on privilege (name);
