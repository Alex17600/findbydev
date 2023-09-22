CREATE TABLE genders (
   id_gender SERIAL,
   name VARCHAR(50) NOT NULL,
   PRIMARY KEY (id_gender)
);

CREATE TABLE status (
   id_status SERIAL,
   name VARCHAR(50) NOT NULL,
   PRIMARY KEY (id_status)
);

CREATE TABLE preference (
   id_preference SERIAL,
   langage VARCHAR(50) NOT NULL,
   framework VARCHAR(50),
   experience VARCHAR(50),
   PRIMARY KEY (id_preference)
);

CREATE TABLE users (
   id_user SERIAL,
   lastname VARCHAR(50) NOT NULL,
   firstname VARCHAR(50) NOT NULL,
   town VARCHAR(50) NOT NULL,
   birthday VARCHAR(50) NOT NULL,
   mail VARCHAR(75) NOT NULL,
   password VARCHAR(255) NOT NULL,
   activeAccount BOOLEAN NOT NULL,
   description VARCHAR(255),
   popularity INTEGER,
   photo BIT VARYING(50),
   gitProfile VARCHAR(50),
   id_gender INTEGER NOT NULL,
   type VARCHAR(50),
   PRIMARY KEY (id_user),
   FOREIGN KEY (id_gender) REFERENCES genders (id_gender)
);

CREATE TABLE matches (
   id_matche SERIAL,
   dateHour TIMESTAMP,
   user1 INTEGER,
   user2 INTEGER,
   id_status INTEGER NOT NULL,
   PRIMARY KEY (id_matche),
   FOREIGN KEY (id_status) REFERENCES status (id_status)
);

CREATE TABLE alerts (
   id_alert SERIAL,
   id_reported_user INTEGER NOT NULL,
   reason VARCHAR(100) NOT NULL,
   date_alert TIMESTAMP NOT NULL,
   id_user INTEGER NOT NULL,
   PRIMARY KEY (id_alert),
   FOREIGN KEY (id_user) REFERENCES users (id_user)
);

CREATE TABLE conversation (
   id_conversation SERIAL,
   id_user INTEGER NOT NULL,
   PRIMARY KEY (id_conversation),
   FOREIGN KEY (id_user) REFERENCES users (id_user)
);

CREATE TABLE message (
   id_message SERIAL,
   id_sender INTEGER NOT NULL,
   id_receiver INTEGER NOT NULL,
   contain VARCHAR(1000) NOT NULL,
   date_hour TIMESTAMP,
   id_conversation INTEGER NOT NULL,
   PRIMARY KEY (id_message),
   FOREIGN KEY (id_conversation) REFERENCES conversation (id_conversation)
);

CREATE TABLE participate (
   id_user INTEGER,
   id_matche INTEGER,
   PRIMARY KEY (id_user, id_matche),
   FOREIGN KEY (id_user) REFERENCES users (id_user),
   FOREIGN KEY (id_matche) REFERENCES matches (id_matche)
);

CREATE TABLE user_preference (
   id_user INTEGER,
   id_preference INTEGER,
   PRIMARY KEY (id_user, id_preference),
   FOREIGN KEY (id_user) REFERENCES users (id_user),
   FOREIGN KEY (id_preference) REFERENCES preference (id_preference)
);

-- Insertions pour la table gender
INSERT INTO genders (name) VALUES ('Male');
INSERT INTO genders (name) VALUES ('Female');
INSERT INTO genders (name) VALUES ('Non-binary');
INSERT INTO genders (name) VALUES ('Transgender');
INSERT INTO genders (name) VALUES ('Other');
INSERT INTO genders (name) VALUES ('Prefer not to say');

-- Insertion d'utilisateurs avec des pseudonymes Git aléatoires et des valeurs factices pour la photo
INSERT INTO users (lastname, firstname, town, birthday, mail, password, activeAccount, description, popularity, photo, gitProfile, id_gender, type)
VALUES
   ('Doe', 'John', 'New York', '1990-05-15', 'user@user.user', '$2y$10$/VIidlrV3nKr7IUmwpOvOeHytFydZ49uGe45U6dHKj/SxQqmS2RN6', true, 'Software Engineer', 100, '', 'john_git', 1, 'U'),
   ('admin', 'admin', 'Philadelphia', '1998-08-07', 'admin@admin.admin', '$2y$10$z4FFvwvJrT9P8RGEpW3/yePVegB7OZQYbk50.WIUuOcu0xsZyAIN2', true, 'QA Engineer', 78, '', 'alex_git', 2, 'A');
