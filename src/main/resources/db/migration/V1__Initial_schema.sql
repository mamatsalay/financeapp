-- V1__Initial_schema.sql

-- Create the users table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Create the user_roles table to store roles associated with users
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role VARCHAR(255) NOT NULL,
                            CONSTRAINT fk_user_roles_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create the incomes table
CREATE TABLE income (
                         id BIGSERIAL PRIMARY KEY,
                         amount DECIMAL(19, 2) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         date DATE NOT NULL,
                         userInfo_id BIGINT NOT NULL,
                         CONSTRAINT fk_incomes_user FOREIGN KEY(userInfo_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create the expenses table
CREATE TABLE expense (
                          id BIGSERIAL PRIMARY KEY,
                          amount DECIMAL(19, 2) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          date DATE NOT NULL,
                          userInfo_id BIGINT NOT NULL,
                          CONSTRAINT fk_expenses_user FOREIGN KEY(userInfo_id) REFERENCES users(id) ON DELETE CASCADE
);