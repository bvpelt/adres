drop table if exists adres;
drop table if exists adres_person;
drop table if exists person;
drop table if exists privilege;
drop table if exists role;
drop table if exists roles_privileges;
drop table if exists "user";
drop table if exists users_roles;

delete
from flyway_schema_history;