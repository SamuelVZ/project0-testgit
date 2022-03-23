
DROP TABLE IF EXISTS clients_accounts;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(200) NOT NULL,
	last_name VARCHAR(200) NOT null,
	age int NOT NULL,
	phone_number VARCHAR (100)
);


CREATE TABLE accounts (
	id SERIAL PRIMARY KEY, 
	account_name VARCHAR(50) NOT NULL,
	account_description VARCHAR(50) NOT NULL
);


create table clients_accounts (
client_id integer not null,
account_id integer not null,
balance INTEGER NOT NULL DEFAULT 0 CHECK(balance >= 0),

constraint fk_client foreign key(client_id) references clients(id) ON DELETE CASCADE,
constraint fk_account foreign key(account_id) references accounts(id) ON DELETE cascade,
primary key (client_id, account_id)
);


INSERT INTO clients (first_name, last_name, age, phone_number) 
VALUES 
('Samuelgcp', 'Valencia', 24, 4567898547),
('Hugo', 'Licon', 18, 65147856954),
('Perla', 'Soriano', 25, 9832652642),
('Bach', 'Tran', 25, 4781238477),
('Daniel', 'Land', 18, 7894561236);

INSERT INTO accounts (account_name, account_description) 
VALUES 
('savings', 'savings account'),
('checking', 'cheking account'),
('money market', 'money market account'),
('certificate of deposit', 'certificate of deposit');


INSERT INTO clients_accounts (client_id, account_id, balance)
VALUES 
(1, 1, 5000),
(1, 2, 458),
(1, 3, 425),
(1, 4, 95),

(2, 1, 600),
(2, 2, 47),
(2, 3, 12),


(3, 1, 9874),
(3, 2, 1234),

(4, 1, 1478);

/*
UPDATE clients ()
SET first_name = ?, 
    last_name = ?, 
    age = ?, 
    phone_number = ?, 
WHERE id = ?;
*/


SELECT *
FROM clients;

SELECT *
FROM accounts ;

select *
from clients_accounts;




/*
 
SELECT * 
FROM clients_accounts 
WHERE client_id = 1
and balance > 400 
and balance <2000; 
 
SELECT ca.client_id, c.first_name, c.last_name, ca.account_id, a.account_name, ca.balance  
FROM clients_accounts ca
JOIN clients c ON ca.client_id = c.id 
JOIN accounts a ON ca.account_id = a.id 
WHERE ca.client_id = 1;

UPDATE clients_accounts
SET client_id  = 1, 
    account_id  = 1, 
    balance  = 2
WHERE client_id  = 1 and account_id = 1;


DELETE FROM clients_accounts WHERE client_id = 2 and account_id  = 1;


CREATE TABLE accounts (
	id SERIAL PRIMARY KEY, 
	account_name VARCHAR(50) NOT NULL,
	balance INTEGER NOT NULL DEFAULT 0 CHECK(balance >= 0),
	client_id INTEGER NOT NULL,
	
	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE
);

INSERT INTO accounts (account_name, balance, client_id) 
VALUES 
('savings', 7000, 1),
('checking', 4521, 1),
('money market', 42341, 1),
('certificate of deposit', 234, 1),

('savings', 54345, 2),
('checking', 45364, 2),
('money market', 4565, 2),
('certificate of deposit', 987, 2),

('savings', 3753, 3),
('checking', 456654, 3),
('money market', 654456, 3),

('savings', 12, 4),
('checking', 32, 4);

*/
