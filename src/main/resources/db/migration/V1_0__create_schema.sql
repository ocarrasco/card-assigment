--
-- registered_user table def
--
CREATE TABLE IF NOT EXISTS `registered_user`
(
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` varchar(50) NOT NULL UNIQUE,
    `password` TEXT NOT NULL,
    `role` varchar(256) NOT NULL
);

--
-- card table def
--
CREATE TABLE IF NOT EXISTS `card`
(
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `description` TEXT,
    `color` varchar(7),
    `status` varchar(12),
    `created_at` timestamp,
    `owner_id` int NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES registered_user(id) ON DELETE CASCADE
);