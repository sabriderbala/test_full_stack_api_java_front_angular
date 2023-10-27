DELETE FROM PARTICIPATE;
DELETE FROM SESSIONS;
DELETE FROM TEACHERS;
DELETE FROM USERS;

ALTER TABLE PARTICIPATE AUTO_INCREMENT = 1;
ALTER TABLE SESSIONS AUTO_INCREMENT = 1;
ALTER TABLE TEACHERS AUTO_INCREMENT = 1;
ALTER TABLE USERS AUTO_INCREMENT = 1;

INSERT INTO TEACHERS (first_name, last_name)
VALUES ('Margot', 'DELAHAYE'),
       ('Hélène', 'THIERCELIN');

INSERT INTO SESSIONS (name,description,`date`,teacher_id,created_at,updated_at)
VALUES ('Session 1','test pour la session 1','2023-10-27 02:40:00',1,'2023-10-27 02:40:00','2023-10-27 02:40:00'),
       ('Session 2','test pour la session 2','2023-04-06 02:40:00',2,'2023-04-06 02:40:00','2023-04-06 02:40:00');

INSERT INTO USERS (first_name, last_name, admin, email, password)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('john', 'wick', true, 'john@test.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq');


INSERT INTO PARTICIPATE (user_id,session_id) VALUES (1,1);
