CREATE TABLE users (
   id UUID NOT NULL,
   login VARCHAR(255),
   password VARCHAR(255),
   role INT,
   CONSTRAINT pk_users PRIMARY KEY (id)
);