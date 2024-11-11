create table users
(
    userid   bigint       not null auto_increment primary key,
    username varchar(24)  not null,
    password varchar(48)  not null,
    email    varchar(128) not null,
    phone    varchar(24)
);

create index users_username_ix on users (username);
create index users_email_ix on users (email);

