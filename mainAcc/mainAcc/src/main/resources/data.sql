-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE table `account_roles`;
-- TRUNCATE table `account_payments`;
-- TRUNCATE table `accounts`;
-- TRUNCATE table `roles`;
-- TRUNCATE table `payments`;
--
-- SET FOREIGN_KEY_CHECKS = 1;

--INSERT INTO `accounts` (`id`,`email`,`password`,`username`) VALUES (1,'Ahmad@miu.edu','$2a$10$7NesjhaJlyRlLJ6kZ7I.6.X.pXWSCYKdYctKcz/ioA4aUFlvFjFNW','Ahmad');



INSERT INTO `roles` (`id`,`name`) VALUES (1,'ROLE_USER');
INSERT INTO `roles` (`id`,`name`) VALUES (2,'ROLE_MODERATOR');
INSERT INTO `roles` (`id`,`name`) VALUES (3,'ROLE_ADMIN');
INSERT INTO `roles` (`id`,`name`) VALUES (4,'ROLE_X');

INSERT INTO `payments` (`id`,`name`) VALUES (1,'CREDIT_CARD');
INSERT INTO `payments` (`id`,`name`) VALUES (2,'BANK_ACCOUNT');
INSERT INTO `payments` (`id`,`name`) VALUES (3,'PAYPAL');

--INSERT INTO `account_roles` (`account_id`,`role_id`) VALUES (1,1);

commit;