create table role
(
    roleid      bigint      not null primary key,
    rolename    varchar(32) not null,
    description varchar(128),
    userid      bigint
);

ALTER TABLE role
ALTER
COLUMN roleid ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME role_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
);

create index role_rolename_ix on role (rolename);
create index role_userid_ix on role (userid);

