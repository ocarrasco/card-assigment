insert into registered_user (id, email, password, role) values
(1, 'admin@email.com', '$2a$10$koq/r0jRqZWqjUYDYiProOQJ04kgj/YuWKhbk0lg3NBun/SehcQ/i', 'ADMIN');

insert into registered_user (id, email, password, role) values
(2, 'member1@email.com', '$2a$10$0rEFoLpSKMB3K.efGKXUWeFdd5ARSWItyvg8szehXiMDp/sRCpg5G', 'MEMBER');

insert into registered_user (id, email, password, role) values
(3, 'member2@email.com', '$2a$10$oNTEadnxrDrU8XGwFyN/8.WBsJoG5HOmRvQF8OmZ7FC.5DH5mx1DS', 'MEMBER');

--
-- Data for admin@email.com
--
insert into card (name, color, description, status, created_at, owner_id) values
('First One', '#55ff33', null, 1, '2023-06-14', 1);

insert into card (name, color, description, status, created_at, owner_id) values
('Second One', null, 'Description for it', 3, '2023-06-14', 1);

insert into card (name, color, description, status, created_at, owner_id) values
('Other One', '#11ddf3', 'Description for it', 3, '2023-06-14', 1);

--
-- Data for member1@email.com
--
insert into card (name, color, description, status, created_at, owner_id) values
('Secret Card', '#55ff33', null, 1, '2023-06-14', 2);

insert into card (name, color, description, status, created_at, owner_id) values
('Public Card', '#333333', 'Description for it', 3, '2023-06-14', 2);

insert into card ( name, color, description, status, created_at, owner_id) values
('Other One', '#22ddf3', 'Description for it', 3, '2023-06-14', 2);