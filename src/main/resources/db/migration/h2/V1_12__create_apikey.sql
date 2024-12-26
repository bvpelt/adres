create table apikey
(
    id          bigint not null primary key,
    apikey      text   not null,
    inuse       boolean
);

create unique index apikey_ix on apikey (apikey, inuse);
