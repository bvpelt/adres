-- persons
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ('Mark', 'de', 'Graaf', 357873212, '2002-02-02');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Marnix', 'de', 'Sweelinck', -33607835, '1989-03-24');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Marianne', 'van der', 'Vaart', 189303173, '1999-02-23');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Peter', 'de', 'Vries', -1164158960, '1989-03-21');
insert into person( firstname, lastname, hash, dateofbirth)
values ( 'Jantine', 'Leene', -1486817262, '1983-04-23');
insert into person( firstname, lastname, hash, dateofbirth)
values ( 'Felix', 'Groothuis', -1842460391, '2001-02-20');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Jan', 'van', 'Zelst', 706630778, '1981-01-15');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Peter', 'van', 'Zelst', -952664917, '1982-02-21');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Cor', 'van', 'Zelst', 519279995, '1983-12-31');
insert into person( firstname, infix, lastname, hash, dateofbirth)
values ( 'Marinus', 'van', 'Zelst', 90334592, '1985-01-31');


-- SELECT setval('adres_id_seq', 10, true); -- next value will be 11

insert into adres_person(adresid, personid)
values ( 1, 1);
insert into adres_person(adresid, personid)
values ( 2, 2);
insert into adres_person(adresid, personid)
values ( 3, 3);
insert into adres_person(adresid, personid)
values ( 4, 4);
insert into adres_person(adresid, personid)
values ( 4, 5);
insert into adres_person(adresid, personid)
values ( 4, 6);
insert into adres_person(adresid, personid)
values ( 5, 7);
insert into adres_person(adresid, personid)
values ( 5, 8);
insert into adres_person(adresid, personid)
values ( 5, 9);
insert into adres_person(adresid, personid)
values (5, 10);

-- SELECT setval('adres_person_id_seq', 10, true); -- next value will be 11