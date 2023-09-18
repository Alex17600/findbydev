-- Création du type Enum pour "gender"
CREATE TYPE gender_enum AS ENUM ('Homme', 'Femme', 'Autre');

-- Création du type Enum pour "status"
CREATE TYPE status_enum AS ENUM ('En attente', 'Mutuel', 'Unilatéral');

CREATE TABLE users (
   Id_user SERIAL PRIMARY KEY,
   lastname VARCHAR(50) NOT NULL,
   firstname VARCHAR(50) NOT NULL,
   town VARCHAR(50) NOT NULL,
   birthday VARCHAR(50) NOT NULL,
   gender gender_enum NOT NULL,
   mail VARCHAR(75) NOT NULL,
   password VARCHAR(255) NOT NULL,
   activeAccount BOOLEAN NOT NULL,
   description VARCHAR(255),
   popularity_ INT,
   photo BYTEA NOT NULL,
   gitProfile VARCHAR(50)
);

CREATE TABLE matches (
   Id_matche SERIAL PRIMARY KEY,
   status status_enum NOT NULL,
   dateHour TIMESTAMPTZ,
   user1 INT,
   user2 INT,
   FOREIGN KEY (user1) REFERENCES users(Id_user),
   FOREIGN KEY (user2) REFERENCES users(Id_user)
);

CREATE TABLE messages (
   Id_messages SERIAL PRIMARY KEY,
   idSender INT NOT NULL,
   idReceiver INT NOT NULL,
   contain VARCHAR(1000) NOT NULL,
   dateHour TIMESTAMPTZ,
   Id_user INT NOT NULL,
   FOREIGN KEY (Id_user) REFERENCES users(Id_user)
);

CREATE TABLE conversations (
   Id_conversation SERIAL PRIMARY KEY,
   Id_messages INT NOT NULL,
   FOREIGN KEY (Id_messages) REFERENCES messages(Id_messages)
);

CREATE TABLE preferences (
   Id_preference SERIAL PRIMARY KEY,
   idUser INT NOT NULL,
   langage VARCHAR(50) NOT NULL,
   framework VARCHAR(50),
   experience VARCHAR(50)
);

CREATE TABLE alerts (
   Id_alert SERIAL PRIMARY KEY,
   idUser INT NOT NULL,
   idReportedUser INT NOT NULL,
   reason VARCHAR(100) NOT NULL,
   dateAlert TIMESTAMPTZ NOT NULL,
   Id_user INT NOT NULL,
   FOREIGN KEY (idUser) REFERENCES users(Id_user),
   FOREIGN KEY (idReportedUser) REFERENCES users(Id_user)
);

CREATE TABLE participate (
   Id_user INT,
   Id_matche INT,
   PRIMARY KEY (Id_user, Id_matche),
   FOREIGN KEY (Id_user) REFERENCES users(Id_user),
   FOREIGN KEY (Id_matche) REFERENCES matches(Id_matche)
);

CREATE TABLE have (
   Id_user INT,
   Id_conversation INT,
   PRIMARY KEY (Id_user, Id_conversation),
   FOREIGN KEY (Id_user) REFERENCES users(Id_user),
   FOREIGN KEY (Id_conversation) REFERENCES conversations(Id_conversation)
);

CREATE TABLE prefer (
   Id_user INT,
   Id_preference INT,
   PRIMARY KEY (Id_user, Id_preference),
   FOREIGN KEY (Id_user) REFERENCES users(Id_user),
   FOREIGN KEY (Id_preference) REFERENCES preferences(Id_preference)
);

INSERT INTO users (lastname, firstname, town, birthday, gender, mail, password, activeAccount, description, popularity_, photo, gitProfile)
VALUES
   ('Doe', 'John', 'New York', '1990-05-15', 'Homme', 'john.doe@example.com', 'motdepasse1', TRUE, 'Développeur passionné', 85, 'photo1.jpg', 'johndoe'),
   ('Smith', 'Alice', 'Los Angeles', '1988-08-21', 'Femme', 'alice.smith@example.com', 'motdepasse2', TRUE, 'Ingénieure logiciel', 92, 'photo2.jpg', 'alicesmith'),
   ('Brown', 'Robert', 'Chicago', '1992-03-10', 'Homme', 'robert.brown@example.com', 'motdepasse3', TRUE, 'Designer UX/UI', 78, 'photo3.jpg', 'robertbrown');

INSERT INTO matches (status, dateHour, user1, user2)
VALUES
   ('Mutuel', '2023-09-10 14:30:00', 1, 2),
   ('Unilatéral', '2023-09-11 10:15:00', 2, 3),
   ('Mutuel', '2023-09-12 16:45:00', 1, 3);

INSERT INTO messages (idSender, idReceiver, contain, dateHour, Id_user)
VALUES
   (1, 2, 'Salut, comment ça va ?', '2023-09-10 14:35:00', 1),
   (2, 1, 'Ça va bien, merci !', '2023-09-10 14:40:00', 2),
   (3, 1, 'Bonjour !', '2023-09-11 10:20:00', 3);

INSERT INTO conversations (Id_messages)
VALUES
   (1),
   (2),
   (3);

INSERT INTO preferences (idUser, langage, framework, experience)
VALUES
   (1, 'Java', 'Spring', 'Avancé'),
   (1, 'Python', 'Django', 'Avancé'),
   (2, 'JavaScript', 'React', 'Débutant'),
   (3, 'C#', 'ASP.NET', 'Intermédiaire');


INSERT INTO alerts (idUser, idReportedUser, reason, dateAlert, Id_user)
VALUES
   (2, 1, 'Harcèlement', '2023-09-11 11:30:00', 2),
   (3, 1, 'Contenu inapproprié', '2023-09-12 09:45:00', 3);

INSERT INTO participate (Id_user, Id_matche)
VALUES
   (1, 1),
   (2, 2),
   (3, 3);

INSERT INTO have (Id_user, Id_conversation)
VALUES
   (1, 1),
   (2, 2),
   (3, 3);

INSERT INTO prefer (Id_user, Id_preference)
VALUES
   (1, 1),
   (1, 2),
   (2, 3),
   (3, 4);