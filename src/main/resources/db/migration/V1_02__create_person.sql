create table person
(
    personid  bigint not null auto_increment primary key,
    firstname text   not null,
    infix     text,
    lastname  text   not null,
    hash      int
);

