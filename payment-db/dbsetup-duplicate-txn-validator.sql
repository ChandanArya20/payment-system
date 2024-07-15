DROP DATABASE validations;


DROP USER 'validations'@'%';


-- Creates databases
CREATE DATABASE validations;


-- Creates user & grants permission
CREATE USER 'validations'@'%' IDENTIFIED BY 'cptraining';


-- GRANT START Either this 
GRANT ALL ON *.* TO 'validations'@'%' ;

-- or this

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, RELOAD, PROCESS, REFERENCES, INDEX, ALTER, SHOW DATABASES, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, REPLICATION SLAVE, REPLICATION CLIENT, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, CREATE USER, EVENT, TRIGGER ON *.* TO 'validations'@'%' ;
-- GRANT END Either this.

-- Create Tables validations Schema Start***

CREATE TABLE validations.`merchant_payment_request` (
 `id` int NOT NULL AUTO_INCREMENT,
 `merchantTransactionReference` varchar(50) NOT NULL,
 `transactionRequest` text DEFAULT NULL,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`),
UNIQUE KEY (`merchantTransactionReference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the user table
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    endUserId VARCHAR(255) UNIQUE NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255) UNIQUE, 
    phone VARCHAR(20) UNIQUE
);

-- Create the user_log table
CREATE TABLE user_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    endUserId VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    updatedAt timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2)
);


-- Create the payment table
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    endUserId VARCHAR(255) NOT NULL,
    creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
    merchantTxnRef VARCHAR(255),
    amount DECIMAL(10, 2),
    currency VARCHAR(10),
    country VARCHAR(10),
    locale VARCHAR(10),
    shopperStatement VARCHAR(255),
    successURL VARCHAR(255),
    failureURL VARCHAR(255),
    paymentMethod VARCHAR(50),
    paymentType VARCHAR(50),
    providerId VARCHAR(50),
    FOREIGN KEY (endUserId) REFERENCES user(endUserId)
);
