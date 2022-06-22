INSERT INTO Role (ROLE) VALUES ('super_user');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (1, '*');
INSERT INTO User (USER_ID, NAME, HASHPWD, ROLE_ID, STATE) VALUES ('super_user', 'superUser', '8dbdda48fb8748d6746f1965824e966a', 1, 1);


INSERT INTO Role (ROLE) VALUES ('manager');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (2, 'Q');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (2, 'U');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (2, 'D');
INSERT INTO User (USER_ID, NAME, HASHPWD, ROLE_ID, STATE) VALUES ('manager', 'manager', '8dbdda48fb8748d6746f1965824e966a', 2, 1);


INSERT INTO Role (ROLE) VALUES ('operator');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (3, 'Q');
INSERT INTO Role_Accesscontrol (ROLE_ID, NOUNS) VALUES (3, 'C');
INSERT INTO User (USER_ID, NAME, HASHPWD, ROLE_ID, STATE) VALUES ('operator', 'operator', '8dbdda48fb8748d6746f1965824e966a', 3, 1);


INSERT INTO Company (NAME, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES ('company_a', 'super_user', '2022-06-21 12:00:00', 'super_user', '2022-06-21 12:00:00');
INSERT INTO Company (NAME, ADDRESS, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES ('company_b', 'address_b', 'super_user', '2022-06-21 09:00:00', 'super_user', '2022-06-21 09:00:00');


INSERT INTO Client (COMPANY_ID, NAME, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES (2, 'client_a', 'super_user', '2022-06-21 11:00:00', 'super_user', '2022-06-21 11:00:00');
INSERT INTO Client (COMPANY_ID, NAME, EMAIL, PHONE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES (1, 'client_b', 'test@email.com', '0123456789', 'super_user', '2022-06-21 08:00:00', 'super_user', '2022-06-21 08:00:00');
