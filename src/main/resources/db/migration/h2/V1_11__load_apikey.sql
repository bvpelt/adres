-- api keys
insert into apikey (id, apikey, inuse)
values (1, 'f0583805-03f6-4c7f-8e40-f83f55b7c077', true);
insert into apikey (id, apikey, inuse)
values (2, '1fb57aec-f232-4e6a-8443-8bdcf3b5a19e', true);
insert into apikey (id, apikey, inuse)
values (3, '0d495026-15e5-4feb-9e5a-c46877059ad5', true);
insert into apikey (id, apikey, inuse)
values (4, 'f5e1c165-6b57-45a0-b6d2-acbb67b1cba2', false);
insert into apikey (id, apikey, inuse)
values (5, 'df80dce8-4a26-4811-bcbd-af30fe7b0f6b', false);
insert into apikey (id, apikey, inuse)
values (6, 'fd11bcff-1e14-43d1-987e-db7a4ed9aa32', false);
insert into apikey (id, apikey, inuse)
values (7, 'b1b78bbf-e0a1-415a-8ecf-789ffc0432d5', false);
insert into apikey (id, apikey, inuse)
values (8, '6e1d85a1-3326-43f3-998e-119dacf9f6da', false);
insert into apikey (id, apikey, inuse)
values (9, '021852eb-f08a-4d30-a2c6-568821c99ec5', false);
insert into apikey (id, apikey, inuse)
values (10, '6817b040-e09b-4e66-9d9b-064a8628bcc3', false);
insert into apikey (id, apikey, inuse)
values (11, 'f115263c-99cd-4531-8e23-9d355b4021b8', false);
insert into apikey (id, apikey, inuse)
values (12, '11c35296-4733-43bd-a863-f859f466d641', false);
insert into apikey (id, apikey, inuse)
values (13, 'b647df64-accc-494c-8fd8-2280c1850b69', false);
insert into apikey (id, apikey, inuse)
values (14, 'a11eafbd-a74b-4947-8bc7-4e363afa7cfc', false);
insert into apikey (id, apikey, inuse)
values (15, '4b386ae6-63de-4879-8b7a-1184c098c942', false);
insert into apikey (id, apikey, inuse)
values (16, '812ca169-de19-434e-8709-cc24eb6a41cb', false);

ALTER TABLE apikey
    ALTER COLUMN id RESTART WITH 17;
