SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE roles AUTO_INCREMENT = 1;

INSERT INTO roles (name) VALUES("ROLE_USER");
INSERT INTO roles (name) VALUES("ROLE_ADMIN");

INSERT INTO users (password, first_name, last_name, age, email)
VALUES ('$2a$12$QCX2clmgGzS2RuVXSSR9Uew7DMjuG9nR.0I3WRY9KSZa4xG5XyLV2', 'User',
        'User', 25, 'user@gmail.com');

INSERT INTO users (password, first_name, last_name, age, email)
VALUES ('$2a$12$Y1rKvLcfs6GvdQB0.6PJh.T/elEM7aIv8S1nlgIgHr9cNZGkcMH3O', 'Admin',
        'Admin', 35, 'admin@gmail.com');

INSERT INTO user_roles (user_id, role_id) VALUES(1,1);

INSERT INTO user_roles (user_id, role_id) VALUES(2,2), (2,1);