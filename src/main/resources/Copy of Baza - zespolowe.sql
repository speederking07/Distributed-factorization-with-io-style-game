CREATE TABLE `user` (
  `username` varchar(255) PRIMARY KEY,
  `password` varchar(255),
  `created_at` timestamp,
  `email` varchar(255),
  `point` int,
  `color` hex,
  `pattern_id` int
);

CREATE TABLE `pattern` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `json_pattern` varchar(255)
);

CREATE TABLE `user_role` (
  `username` varchar(255),
  `role_id` int
);

CREATE TABLE `role` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `match` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `started_at` timestamp
);

CREATE TABLE `match_participants` (
  `username` varchar(255),
  `match_id` int,
  `joined_at` timestamp,
  `score` int
);

CREATE TABLE `factorization` (
  `number` bigint_unsigned PRIMARY KEY,
  `prime_1` bigint_unsigned,
  `prime_2` bigint_unsigned
);

ALTER TABLE `user_role` ADD FOREIGN KEY (`username`) REFERENCES `user` (`username`);

ALTER TABLE `user_role` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `user` ADD FOREIGN KEY (`pattern_id`) REFERENCES `pattern` (`id`);

ALTER TABLE `match_participants` ADD FOREIGN KEY (`username`) REFERENCES `user` (`username`);

ALTER TABLE `match_participants` ADD FOREIGN KEY (`match_id`) REFERENCES `match` (`id`);
