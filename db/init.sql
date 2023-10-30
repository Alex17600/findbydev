CREATE TYPE status AS ENUM ('VALIDE', 'EN_ATTENTE', 'REFUSE');
CREATE TABLE technology(
   Id_technology SERIAL,
   name VARCHAR(50)  NOT NULL,
   image_path VARCHAR(50) ,
   PRIMARY KEY(Id_technology)
);

CREATE TABLE gender(
   Id_gender SERIAL,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_gender)
);

CREATE TABLE experience(
   Id_experience SERIAL,
   descriptif VARCHAR(400) ,
   PRIMARY KEY(Id_experience)
);

CREATE TABLE _user_(
   Id_user SERIAL,
   pseudo VARCHAR(50)  NOT NULL,
   lastname VARCHAR(50)  NOT NULL,
   firstname VARCHAR(50)  NOT NULL,
   town VARCHAR(50)  NOT NULL,
   birthday VARCHAR(50)  NOT NULL,
   mail VARCHAR(75)  NOT NULL,
   password VARCHAR(255)  NOT NULL,
   active_account BOOLEAN NOT NULL,
   description VARCHAR(255) ,
   popularity INTEGER,
   photo VARCHAR,
   git_profile VARCHAR(50) ,
   type VARCHAR(10)  NOT NULL,
   Id_gender INTEGER NOT NULL,
   PRIMARY KEY(Id_user),
   FOREIGN KEY(Id_gender) REFERENCES gender(Id_gender)
);

CREATE TABLE alert(
   Id_alert SERIAL,
   id_reported_user INTEGER NOT NULL,
   reason VARCHAR(100)  NOT NULL,
   date_alert TIMESTAMP NOT NULL,
   Id_user INTEGER NOT NULL,
   PRIMARY KEY(Id_alert),
   FOREIGN KEY(Id_user) REFERENCES _user_(Id_user)
);

CREATE TABLE prefer(
   Id_user INTEGER,
   Id_technology INTEGER,
   PRIMARY KEY(Id_user, Id_technology),
   FOREIGN KEY(Id_user) REFERENCES _user_(Id_user),
   FOREIGN KEY(Id_technology) REFERENCES technology(Id_technology)
);

CREATE TABLE _match_(
   Id_user_receiver INTEGER,
   Id_user_sender INTEGER,
   date_hour DATE,
   current_status VARCHAR,
   is_read BOOLEAN,
   PRIMARY KEY(Id_user_receiver, Id_user_sender),
   FOREIGN KEY(Id_user_receiver) REFERENCES _user_(Id_user),
   FOREIGN KEY(Id_user_sender) REFERENCES _user_(Id_user)
);

CREATE TABLE realise(
   Id_user INTEGER,
   Id_experience INTEGER,
   PRIMARY KEY(Id_user, Id_experience),
   FOREIGN KEY(Id_user) REFERENCES _user_(Id_user),
   FOREIGN KEY(Id_experience) REFERENCES experience(Id_experience)
);

CREATE TABLE concerne(
   Id_technology INTEGER,
   Id_experience INTEGER,
   PRIMARY KEY(Id_technology, Id_experience),
   FOREIGN KEY(Id_technology) REFERENCES technology(Id_technology),
   FOREIGN KEY(Id_experience) REFERENCES experience(Id_experience)
);

CREATE TABLE _like_(
   Id_user INTEGER,
   Id_experience INTEGER,
   PRIMARY KEY(Id_user, Id_experience),
   FOREIGN KEY(Id_user) REFERENCES _user_(Id_user),
   FOREIGN KEY(Id_experience) REFERENCES experience(Id_experience)
);

CREATE TABLE conversation(
   Id_conversation SERIAL,
   date_debut TIMESTAMP NOT NULL,
   archived BOOLEAN NOT NULL,
   user_sender INTEGER NOT NULL,
   user_receiver INTEGER NOT NULL,
   PRIMARY KEY(Id_conversation),
   FOREIGN KEY( user_sender) REFERENCES _user_(Id_user),
   FOREIGN KEY(user_receiver) REFERENCES _user_(Id_user)
);

CREATE TABLE message (
   Id_message SERIAL,
   contain VARCHAR(1000) NOT NULL,
   date_hour TIMESTAMP NOT NULL,
   Id_conversation INTEGER NOT NULL,
   Id_user_sender INTEGER NOT NULL, 
   Id_user_receiver INTEGER NOT NULL, 
   PRIMARY KEY(Id_message),
   FOREIGN KEY(Id_conversation) REFERENCES conversation(Id_conversation),
   FOREIGN KEY(Id_user_sender) REFERENCES _user_(Id_user),
   FOREIGN KEY(Id_user_receiver) REFERENCES _user_(Id_user)
);



INSERT INTO gender (name)
VALUES ('Homme');

INSERT INTO gender (name)
VALUES ('Femme');

INSERT INTO gender (name)
VALUES ('Autre');

-- Insertion d'utilisateurs avec des pseudonymes Git aléatoires et des valeurs factices pour la photo
INSERT INTO _user_ (pseudo, lastname, firstname, town, birthday, mail, password, active_account, description, popularity, photo, git_profile, id_gender, type)
VALUES
  ('ByteWizard42', 'Doe', 'John', 'New York', '1990-05-15', 'user@user.user', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Software Engineer', 100, '', 'john_git', 1, 'U'),
  ('CodeNinjaX', 'admin', 'admin', 'Philadelphia', '1998-08-07', 'admin@admin.admin', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'QA Engineer', 78, '', 'alex_git', 2, 'A'),
  ('PixelPirate', 'Doe', 'John', 'New York', '1990-05-15', 'john@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Web Developer', 85, '', 'johndoe', 1, 'U'),
  ('GeekGuru123', 'Smith', 'Alice', 'Los Angeles', '1988-03-20', 'alice@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Software Engineer', 92, '', 'alicesmith', 2, 'U'),
  ('TechJunkie42', 'Brown', 'Michael', 'Chicago', '1992-09-10', 'michael@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', false, 'Data Scientist', 78, '', 'michaelbrown', 1, 'U'),
  ('DigiDungeonMaster', 'Johnson', 'Emily', 'Houston', '1985-12-05', 'emily@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'UX Designer', 89, '', 'emilyjohnson', 2, 'U'),
  ('CyberspaceHero', 'Williams', 'Daniel', 'San Francisco', '1991-07-25', 'daniel@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Full Stack Developer', 88, '', 'danwilliams', 1, 'U'),
  ('QuantumCoder', 'Lee', 'Olivia', 'Boston', '1987-04-12', 'olivia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Product Manager', 91, '', 'oliviale', 2, 'U'),
  ('DataVoyagerX', 'Garcia', 'Matthew', 'Seattle', '1989-08-30', 'matthew@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', false, 'Network Engineer', 82, '', 'matthewgarcia', 1, 'U'),
  ('DataVoyagerX', 'Martinez', 'Sophia', 'Austin', '1993-02-18', 'sophia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Frontend Developer', 86, '', 'sophiamartinez', 2, 'U'),
  ('BinarySorcerer', 'Lopez', 'William', 'Miami', '1986-11-08', 'william@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Database Administrator', 79, '', 'williamlopez', 1, 'U'),
  ('AIWhisperer', 'Harris', 'Mia', 'Denver', '1990-06-22', 'mia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Graphic Designer', 87, '', 'miaharris', 2, 'U');

INSERT INTO "_match_" (Id_user_receiver, Id_user_sender, date_hour, current_status) 
VALUES 
(1, 2, '2023-09-27 12:00:00', 'EN_ATTENTE'),
(2, 1, '2023-09-27 12:30:00', 'EN_ATTENTE'),
(3, 4, '2023-09-27 13:00:00', 'EN_ATTENTE'),
(4, 3, '2023-09-27 13:30:00', 'EN_ATTENTE'),
(5, 6, '2023-09-27 14:00:00', 'EN_ATTENTE'),
(6, 5, '2023-09-27 14:30:00', 'EN_ATTENTE'),
(7, 8, '2023-09-27 15:00:00', 'EN_ATTENTE'),
(8, 7, '2023-09-27 15:30:00', 'EN_ATTENTE'),
(9, 10, '2023-09-27 16:00:00', 'EN_ATTENTE'),
(10, 9, '2023-09-27 16:30:00', 'EN_ATTENTE');

INSERT INTO experience (descriptif) VALUES
  ('Développement d''une application web'),
  ('Gestion de projet agile'),
  ('Conception d''une base de données'),
  ('Analyse de données statistiques'),
  ('Création de contenu multimédia');

INSERT INTO realise (Id_user, Id_experience) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5);

  INSERT INTO _like_ (Id_user, Id_experience) VALUES
  (1, 1),
  (2, 3),
  (3, 2),
  (4, 5),
  (5, 4);

  INSERT INTO technology (name, image_path) VALUES
  ('Java', 'java.png'),
  ('JavaScript', 'javascript.png'),
  ('Python', 'python.png'),
  ('C++', 'cpp.png'),
  ('React', 'react.png');


INSERT INTO concerne (Id_technology, Id_experience) VALUES
  (1, 1),
  (2, 3),
  (3, 2),
  (4, 5),
  (5, 4);


INSERT INTO prefer (Id_user, Id_technology) VALUES 
(1, 2), 
(2, 3), 
(3, 1), 
(4, 4), 
(5, 2);


INSERT INTO alert (id_reported_user, reason, date_alert, Id_user) 
VALUES 
(2, 'Harcèlement', '2023-09-27 14:30:00', 1),
(3, 'Spam', '2023-09-26 10:45:00', 2),
(1, 'Contenu inapproprié', '2023-09-25 16:15:00', 3),
(4, 'Comportement abusif', '2023-09-24 19:20:00', 4),
(2, 'Harcèlement', '2023-09-23 08:55:00', 5);


-- Insérer des conversations avec des messages

-- Conversation 1 entre l'utilisateur 1 et l'utilisateur 2
INSERT INTO conversation (date_debut, archived, user1, user2)
VALUES ('2023-09-27 12:00:00', false, 1, 2);

-- Messages pour la conversation 1
INSERT INTO message (contain, date_hour, Id_conversation, Id_user_sender, Id_user_receiver, id_user)
VALUES
  ('Salut, comment ça va ?', '2023-09-27 12:01:00', 1, 1, 2, 1),
  ('Salut ! Ça va bien, merci !', '2023-09-27 12:02:00', 1, 2, 1, 2),
  ('Qu''est-ce que tu fais en ce moment ?', '2023-09-27 12:03:00', 1, 1, 2, 1),
  ('Je travaille sur un projet de développement web. Et toi ?', '2023-09-27 12:04:00', 1, 2, 1, 2);

-- Conversation 2 entre l'utilisateur 3 et l'utilisateur 4
INSERT INTO conversation (date_debut, archived,user1, user2)
VALUES ('2023-09-27 14:00:00', false, 3, 4);

-- Messages pour la conversation 2
INSERT INTO message (contain, date_hour, Id_conversation, Id_user_sender, Id_user_receiver, id_user)
VALUES
  ('Salut, comment ça va ?', '2023-09-27 14:01:00', 2, 3, 4, 3),
  ('Ça va bien, merci !', '2023-09-27 14:02:00', 2, 4, 3, 4),
  ('Tu veux faire quelque chose ce week-end ?', '2023-09-27 14:03:00', 2, 3, 4, 3),
  ('Je suis occupé ce week-end. Peut-être la semaine prochaine ?', '2023-09-27 14:04:00', 2, 4, 3, 4);

-- Conversation 3 entre l'utilisateur 5 et l'utilisateur 6
INSERT INTO conversation (date_debut, archived, user1, user2)
VALUES ('2023-09-27 16:00:00', false, 5, 6);

-- Messages pour la conversation 3
INSERT INTO message (contain, date_hour, Id_conversation, Id_user_sender, Id_user_receiver)
VALUES
  ('Bonjour, ça va ?', '2023-09-27 16:01:00', 3, 5, 6),
  ('Ça va, et toi ?', '2023-09-27 16:02:00', 3, 6, 5),
  ('J''ai une question à propos du projet sur lequel nous travaillons.', '2023-09-27 16:03:00', 3, 5, 6),
  ('D''accord, pose ta question.', '2023-09-27 16:04:00', 3, 6, 5);
