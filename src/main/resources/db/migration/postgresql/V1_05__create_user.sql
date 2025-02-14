create table users
(
    id                      bigint       not null primary key,
    username                varchar(24)  not null,
    password                varchar(128) not null,
    email                   varchar(128) not null,
    phone                   varchar(24),
    hash                    int,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    enabled                 boolean,
    apikey_id               bigint
);

create unique index user_username_ix on users (username, account_non_expired, account_non_locked,
                                               credentials_non_expired, enabled);
create unique index user_email_ix on users (email, account_non_expired, account_non_locked, credentials_non_expired,
                                            enabled);

ALTER TABLE users
ALTER
COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME users_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
);
