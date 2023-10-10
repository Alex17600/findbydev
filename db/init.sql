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
   photo VARCHAR(150),
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

CREATE TABLE conversation(
   Id_conversation SERIAL,
   date_debut DATE ,
   archived BOOLEAN,
   Id_user INTEGER NOT NULL,
   PRIMARY KEY(Id_conversation),
   FOREIGN KEY(Id_user) REFERENCES _user_(Id_user)
);

CREATE TABLE message(
   Id_message SERIAL,
   id_sender INTEGER NOT NULL,
   id_receiver INTEGER NOT NULL,
   contain VARCHAR(1000)  NOT NULL,
   date_hour DATE,
   Id_conversation INTEGER NOT NULL,
   PRIMARY KEY(Id_message),
   FOREIGN KEY(Id_conversation) REFERENCES conversation(Id_conversation)
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
   current_status status,
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


INSERT INTO gender (name)
VALUES ('Homme');

INSERT INTO gender (name)
VALUES ('Femme');

INSERT INTO gender (name)
VALUES ('Autre');

-- Insertion d'utilisateurs avec des pseudonymes Git aléatoires et des valeurs factices pour la photo
INSERT INTO _user_ (pseudo, lastname, firstname, town, birthday, mail, password, active_account, description, popularity, photo, git_profile, id_gender, type)
VALUES
  ('p1', 'Doe', 'John', 'New York', '1990-05-15', 'user@user.user', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Software Engineer', 100, '', 'john_git', 1, 'U'),
  ('p2', 'admin', 'admin', 'Philadelphia', '1998-08-07', 'admin@admin.admin', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'QA Engineer', 78, '', 'alex_git', 2, 'A'),
  ('p3', 'Doe', 'John', 'New York', '1990-05-15', 'john@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Web Developer', 85, '', 'johndoe', 1, 'U'),
  ('p4', 'Smith', 'Alice', 'Los Angeles', '1988-03-20', 'alice@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Software Engineer', 92, '', 'alicesmith', 2, 'U'),
  ('p5', 'Brown', 'Michael', 'Chicago', '1992-09-10', 'michael@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', false, 'Data Scientist', 78, '', 'michaelbrown', 1, 'U'),
  ('p6', 'Johnson', 'Emily', 'Houston', '1985-12-05', 'emily@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'UX Designer', 89, '', 'emilyjohnson', 2, 'U'),
  ('p7', 'Williams', 'Daniel', 'San Francisco', '1991-07-25', 'daniel@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Full Stack Developer', 88, '', 'danwilliams', 1, 'U'),
  ('p8', 'Lee', 'Olivia', 'Boston', '1987-04-12', 'olivia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Product Manager', 91, '', 'oliviale', 2, 'U'),
  ('p9', 'Garcia', 'Matthew', 'Seattle', '1989-08-30', 'matthew@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', false, 'Network Engineer', 82, '', 'matthewgarcia', 1, 'U'),
  ('p10', 'Martinez', 'Sophia', 'Austin', '1993-02-18', 'sophia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Frontend Developer', 86, '', 'sophiamartinez', 2, 'U'),
  ('p11', 'Lopez', 'William', 'Miami', '1986-11-08', 'william@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Database Administrator', 79, '', 'williamlopez', 1, 'U'),
  ('p12', 'Harris', 'Mia', 'Denver', '1990-06-22', 'mia@example.com', '$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W', true, 'Graphic Designer', 87, '', 'miaharris', 2, 'U');

INSERT INTO "_match_" (Id_user_receiver, Id_user_sender, date_hour, current_status) 
VALUES 
(1, 2, '2023-09-27 12:00:00', 'VALIDE'),
(2, 1, '2023-09-27 12:30:00', 'EN_ATTENTE'),
(3, 4, '2023-09-27 13:00:00', 'REFUSE'),
(4, 3, '2023-09-27 13:30:00', 'VALIDE'),
(5, 6, '2023-09-27 14:00:00', 'EN_ATTENTE'),
(6, 5, '2023-09-27 14:30:00', 'REFUSE'),
(7, 8, '2023-09-27 15:00:00', 'VALIDE'),
(8, 7, '2023-09-27 15:30:00', 'EN_ATTENTE'),
(9, 10, '2023-09-27 16:00:00', 'REFUSE'),
(10, 9, '2023-09-27 16:30:00', 'VALIDE');

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

