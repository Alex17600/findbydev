CREATE TABLE preference(
   id_preference SERIAL,
   langage VARCHAR(50)  NOT NULL,
   framework VARCHAR(50) ,
   experience VARCHAR(50) ,
   PRIMARY KEY(id_preference)
);

CREATE TABLE roles(
   id_roles SERIAL,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_roles)
);

CREATE TABLE genders(
   id_genders SERIAL,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_genders)
);

CREATE TABLE status(
   id_status SERIAL,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_status)
);

CREATE TABLE users(
   id_user SERIAL,
   lastname VARCHAR(50)  NOT NULL,
   firstname VARCHAR(50)  NOT NULL,
   town VARCHAR(50)  NOT NULL,
   birthday VARCHAR(50)  NOT NULL,
   mail VARCHAR(75)  NOT NULL,
   password VARCHAR(255)  NOT NULL,
   activeAccount BOOLEAN NOT NULL,
   description VARCHAR(255) ,
   popularity INTEGER,
   photo BIT VARYING(50)  NOT NULL,
   gitProfile VARCHAR(50) ,
   id_genders INTEGER NOT NULL,
   id_roles INTEGER NOT NULL,
   PRIMARY KEY(id_user),
   FOREIGN KEY(id_genders) REFERENCES genders(id_genders),
   FOREIGN KEY(id_roles) REFERENCES roles(id_roles)
);

CREATE TABLE matches(
   id_matche SERIAL,
   dateHour TIMESTAMP,
   user1 INTEGER,
   user2 INTEGER,
   id_status INTEGER NOT NULL,
   PRIMARY KEY(id_matche),
   FOREIGN KEY(id_status) REFERENCES status(id_status)
);

CREATE TABLE alerts(
   id_alert SERIAL,
   id_reported_user INTEGER NOT NULL,
   reason VARCHAR(100)  NOT NULL,
   date_alert TIMESTAMP NOT NULL,
   id_user INTEGER NOT NULL,
   PRIMARY KEY(id_alert),
   FOREIGN KEY(id_user) REFERENCES users(id_user)
);

CREATE TABLE conversation(
   id_conversation SERIAL,
   id_user INTEGER NOT NULL,
   PRIMARY KEY(id_conversation),
   FOREIGN KEY(id_user) REFERENCES users(id_user)
);

CREATE TABLE message(
   id_message SERIAL,
   id_sender INTEGER NOT NULL,
   id_receiver INTEGER NOT NULL,
   contain VARCHAR(1000)  NOT NULL,
   date_hour TIMESTAMP,
   id_conversation INTEGER NOT NULL,
   PRIMARY KEY(id_message),
   FOREIGN KEY(id_conversation) REFERENCES conversation(id_conversation)
);

CREATE TABLE participate(
   id_user INTEGER,
   id_matche INTEGER,
   PRIMARY KEY(id_user, id_matche),
   FOREIGN KEY(id_user) REFERENCES users(id_user),
   FOREIGN KEY(id_matche) REFERENCES matches(id_matche)
);

CREATE TABLE user_preference(
   id_user INTEGER,
   id_preference INTEGER,
   PRIMARY KEY(id_user, id_preference),
   FOREIGN KEY(id_user) REFERENCES users(id_user),
   FOREIGN KEY(id_preference) REFERENCES preference(id_preference)
);
