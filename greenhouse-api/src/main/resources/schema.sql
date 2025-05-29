CREATE SCHEMA greenhouse;
USE greenhouse;

CREATE TABLE greenhouse.users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    emailAddress VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(40) NOT NULL
);

CREATE TABLE greenhouse.clients (
    clientID INT AUTO_INCREMENT PRIMARY KEY,
    Cover DECIMAL(15, 2) NOT NULL,
    Premium DECIMAL(10,2) NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES greenhouse.users(id) ON DELETE CASCADE
);

CREATE TABLE greenhouse.plan (
    planID INT AUTO_INCREMENT PRIMARY KEY,
    DOB DATE NOT NULL,
    `Dependency?` BOOLEAN NOT NULL,
    Gender ENUM('Male', 'Female', 'Other') NOT NULL,
    Income DECIMAL(10,2) NOT NULL,
    clientID INT NOT NULL,
    FOREIGN KEY (clientID) REFERENCES greenhouse.clients(clientID) ON DELETE CASCADE
);

INSERT INTO greenhouse.users (name, surname, emailAddress, password)
VALUES ('Brian', 'Mthembu', 'lindtbravos@gmail.com', 'FvckLove');

INSERT INTO greenhouse.clients (Cover, Premium, userID)
VALUES (15200000.99, 1299.99, 1);

INSERT INTO greenhouse.plan (DOB, `Dependency?`, Gender, Income, clientID)
VALUES ('1995-04-12', TRUE, 'Male', 32000.00, 1);