CREATE TABLE users (
   id VARCHAR(255) NOT NULL,
   login VARCHAR(255),
   password VARCHAR(255),
   role VARCHAR(255),
   CONSTRAINT pk_users PRIMARY KEY (id)
);