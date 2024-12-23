create table user
(
    id                      bigint       not null auto_increment primary key,
    username                varchar(24)  not null,
    password                varchar(128) not null,
    email                   varchar(128) not null,
    phone                   varchar(24),
    hash                    int,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    enabled                 boolean
);

create index user_username_ix on user (username);
create unique index user_email_ix on user (email);
