-- adresses
insert into adres (id, street, housenumber, zipcode, city, hash)
values (1, 'Kerkewijk', '0', '3904 KL', 'Veenendaal', -743593666);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (2, 'Kerkewijk', '1', '3904 KL', 'Veenendaal', -743592705);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (3, 'Kerkewijk', '2', '3904 KL', 'Veenendaal', -743591744);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (4, 'Kerkewijk', '3', '3904 KL', 'Veenendaal', -743590783);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (5, 'Kerkewijk', '4', '3904 KL', 'Veenendaal', -743589822);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (6, 'Kerkewijk', '5', '3904 KL', 'Veenendaal', -743588861);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (7, 'Kerkewijk', '6', '3904 KL', 'Veenendaal', -743587900);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (8, 'Kerkewijk', '7', '3904 KL', 'Veenendaal', -743586939);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (9, 'Kerkewijk', '8', '3904 KL', 'Veenendaal', -743585978);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (10, 'Kerkewijk', '9', '3904 KL', 'Veenendaal', -743585017);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (11, 'Kerkewijk', '10', '3904 KL', 'Veenendaal', -742133907);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (12, 'Kerkewijk', '11', '3904 KL', 'Veenendaal', -742132946);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (13, 'Kerkewijk', '12', '3904 KL', 'Veenendaal', -742131985);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (14, 'Kerkewijk', '13', '3904 KL', 'Veenendaal', -742131024);
insert into adres (id, street, housenumber, zipcode, city, hash)
values (15, 'Kerkewijk', '14', '3904 KL', 'Veenendaal', -742130063);

ALTER TABLE adres
    ALTER COLUMN id RESTART WITH 16;