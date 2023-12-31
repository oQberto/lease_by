INSERT INTO users (id, email, username, password, role, is_verified, status, network_status)
VALUES (1, 'user1@gmail.com', 'username1', '1231', 'ADMIN', false, 'ACTIVE', 'OFFLINE'),
       (2, 'user2@gmail.com', 'username2', '1232', 'USER', true, 'ACTIVE', 'OFFLINE'),
       (3, 'user3@gmail.com', 'username3', '1233', 'USER', false, 'ACTIVE', 'ONLINE'),
       (4, 'user4@gmail.com', 'username4', '1234', 'USER', false, 'BLOCKED', 'ONLINE'),
       (5, 'user5@gmail.com', 'username5', '1235', 'USER', true, 'DELETED', 'ONLINE'),
       (6, 'user6@gmail.com', 'username6', '1236', 'USER', true, 'ACTIVE', 'OFFLINE'),
       (7, 'user7@gmail.com', 'username7', '1237', 'USER', false, 'DELETED', 'ONLINE'),
       (8, 'user8@gmail.com', 'username8', '1238', 'USER', true, 'BLOCKED', 'ONLINE'),
       (9, 'user9@gmail.com', 'username9', '1239', 'USER', true, 'BLOCKED', 'ONLINE'),
       (10, 'user10@gmail.com', 'username10', '12310', 'USER', false, 'ACTIVE', 'OFFLINE'),
       (11, 'user11@gmail.com', 'username11', '12311', 'USER', false, 'ACTIVE', 'ONLINE'),
       (12, 'user12@gmail.com', 'username12', '12312', 'USER', true, 'ACTIVE', 'ONLINE'),
       (13, 'user13@gmail.com', 'username13', '12313', 'USER', true, 'ACTIVE', 'OFFLINE'),
       (14, 'user14@gmail.com', 'username14', '12314', 'USER', true, 'DELETED', 'ONLINE'),
       (15, 'user15@gmail.com', 'username15', '12315', 'USER', true, 'BLOCKED', 'OFFLINE');
SELECT setval('users_id_seq', (SELECT max(id) FROM users));

INSERT INTO profile (id, user_id, avatar_img, first_name, last_name, phone_number)
VALUES (1, 1, 'avatar1', 'firstname1', 'lastname1', '(29)123-4567'),
       (2, 2, 'avatar2', 'firstname2', 'lastname2', '(44)123-4567'),
       (3, 3, 'avatar3', 'firstname3', 'lastname3', '(17)123-4567'),
       (4, 4, 'avatar4', 'firstname4', 'lastname4', '(44)123-4567'),
       (5, 5, 'avatar5', 'firstname5', 'lastname5', '(29)123-4567'),
       (6, 6, 'avatar6', 'firstname6', 'lastname6', '(44)123-4567'),
       (7, 7, 'avatar7', 'firstname7', 'lastname7', '(17)123-4567'),
       (8, 8, 'avatar8', 'firstname8', 'lastname8', '(44)123-4567'),
       (9, 9, 'avatar9', 'firstname9', 'lastname9', '(29)123-4567'),
       (10, 10, 'avatar10', 'firstname10', 'lastname10', '(44)123-4567'),
       (11, 11, 'avatar11', 'firstname11', 'lastname11', '(17)123-4567'),
       (12, 12, 'avatar12', 'firstname12', 'lastname12', '(44)123-4567'),
       (13, 13, 'avatar13', 'firstname13', 'lastname13', '(29)123-4567'),
       (14, 14, 'avatar14', 'firstname14', 'lastname14', '(44)123-4567'),
       (15, 15, 'avatar15', 'firstname15', 'lastname15', '(17)123-4567');
SELECT setval('profile_id_seq', (SELECT max(id) FROM profile));

INSERT INTO chat_room(id, sender_id, recipient_id)
VALUES (1, 1, 2),
       (2, 2, 3),
       (3, 3, 4),
       (4, 4, 5),
       (5, 5, 6),
       (6, 6, 7),
       (7, 7, 8);
SELECT setval('chat_room_id_seq', (SELECT max(id) FROM chat_room));

INSERT INTO chat_message(id, chat_id, sender_id, recipient_id, content, sending_time)
VALUES (1, 1, 1, 2, 'content', '2023-10-19 10:23:54'),
       (2, 2, 2, 3, 'content1', '2023-10-19 10:24:54'),
       (3, 3, 3, 4, 'content2', '2023-10-19 11:20:54'),
       (4, 4, 4, 5, 'content3', '2023-10-19 12:24:54');
SELECT setval('chat_message_id_seq', (SELECT max(id) FROM chat_message));

INSERT INTO city (id, name, image)
VALUES (1, 'Minsk', 'minskImage'),
       (2, 'Grodno', 'grodnoImage'),
       (3, 'Brest', 'brestImage'),
       (4, 'Gomel', 'gomelImage'),
       (5, 'Mogilev', 'mogilevImage'),
       (6, 'Vitebsk', 'vitebskImage');
SELECT setval('city_id_seq', (SELECT max(id) FROM city));

INSERT INTO street (id, name, zip_code)
VALUES (1, 'Дружная Улица', '220056'),
       (2, 'Калядная Улица', '220057'),
       (3, 'Солнечная Улица', '220058'),
       (4, 'Улица 40 Лет Победы', '220059'),
       (5, 'Белорусская Улица', '220045'),
       (6, 'Боровая Улица', '2200567'),
       (7, 'Логойская Улица', '220095'),
       (8, 'Славянский Переулок', '220005'),
       (9, 'Улица Пирогова', '220053'),
       (10, 'Янтарная Улица', '220052'),
       (11, 'Верхняя Луговая Улица', '220051'),
       (12, 'Нижняя Боровая Улица', '220050'),
       (13, 'Липовая Улица', '220049'),
       (14, 'Улица Щорса', '220032'),
       (15, 'Сапфировая Улица', '220023'),
       (16, 'Улица Бабушкина', '220098'),
       (17, 'Улица Макаренко', '220001'),
       (18, '1-Й Дальний Переулок', '220000'),
       (19, '1-Й Жирмунский Переулок', '220000'),
       (20, '1-Й Переулок Ивлиева', '220000'),
       (21, '1-Й Тихий Переулок', '220000'),
       (22, '1-Я Трудовая Улица', '220000'),
       (23, '2-Й Жирмунский Переулок', '220000'),
       (24, '3-Й Каравайный Переулок', '220000'),
       (25, '3-Й Переулок Серафимовича', '220000'),
       (26, 'Алитусский Переулок', '220000'),
       (27, 'Асфальтная Улица', '220000'),
       (28, 'Аульская Улица', '220000'),
       (29, 'Беловежская Улица', '220000'),
       (30, 'Белостокская Улица', '220000'),
       (31, 'Блакитный Переулок', '220000'),
       (32, 'Ближняя Улица', '220000'),
       (33, 'Бобровый Переулок', '220000'),
       (34, 'Лидская улица', '220055'),
       (35, 'Люцинская улица', '220055'),
       (36, 'Налибокская улица', '220055'),
       (37, 'Неманская улица', '220055');
SELECT setval('street_id_seq', (SELECT max(id) FROM street));

INSERT INTO address (id, city_id, street_id, house_no)
VALUES (1, 1, 1, 1),
       (2, 2, 2, 2),
       (3, 3, 3, 3),
       (4, 4, 4, 4),
       (5, 5, 5, 5),
       (6, 6, 6, 6),
       (7, 6, 7, 7),
       (8, 5, 8, 8),
       (9, 4, 9, 9),
       (10, 3, 10, 10),
       (11, 2, 11, 11),
       (12, 1, 12, 12),
       (13, 2, 13, 13),
       (14, 3, 14, 14),
       (15, 1, 1, 10),
       (16, 1, 1, 11),
       (17, 1, 1, 12),
       (18, 1, 34, 14),
       (19, 1, 34, 12),
       (20, 1, 35, 33),
       (21, 1, 35, 31),
       (22, 1, 36, 9),
       (23, 1, 36, 37),
       (24, 1, 37, 73),
       (25, 1, 37, 66);
SELECT setval('address_id_seq', (SELECT max(id) FROM address));

INSERT INTO rental (id, user_id, address_id, price, description, status, bedrooms, property_size)
VALUES (1, 1, 1, 100.0, null, 'DRAFT', 3, 35.00),
       (2, 2, 2, 101.0, null, 'BLOCKED', 2, 35.00),
       (3, 3, 3, 102.0, null, 'ACTIVE', 1, 40.00),
       (4, 4, 4, 103.0, null, 'ACTIVE', 1, 40.00),
       (5, 5, 5, 104.0, null, 'PENDING_CONFIRMATION', 1, 40.00),
       (6, 6, 6, 105.0, null, 'ACTIVE', 4, 43.00),
       (7, 7, 7, 106.0, null, 'ACTIVE', 3, 120.00),
       (8, 8, 8, 107.0, null, 'BLOCKED', 1, 48.00),
       (9, 9, 9, 108.0, null, 'ACTIVE', 3, 40.00),
       (10, 10, 10, 109.0, null, 'PENDING_CONFIRMATION', 3, 40.00),
       (11, 11, 11, 110.0, null, 'NO_INFO', 2, 78.00),
       (12, 12, 12, 111.0, null, 'ACTIVE', 1, 78.00),
       (13, 13, 13, 112.0, null, 'BOOKED', 4, 40.00),
       (14, 14, 14, 113.0, null, 'NO_INFO', 1, 40.00),
       (15, 14, 15, 113.0, null, 'PENDING_CONFIRMATION', 1, 40.00),
       (16, 14, 16, 113.0, null, 'ACTIVE', 2, 40.00),
       (17, 14, 17, 113.0, null, 'DELETED', 1, 40.00),
       (18, 1, 18, 110.0, null, 'ACTIVE', 1, 40.00),
       (19, 1, 19, 50.0, null, 'ACTIVE', 1, 43.00),
       (20, 1, 20, 140.0, null, 'ACTIVE', 4, 120.00),
       (21, 1, 21, 100.0, null, 'ACTIVE', 1, 40.00),
       (22, 1, 22, 170.0, null, 'ACTIVE', 1, 120.00),
       (23, 1, 23, 180.0, null, 'ACTIVE', 1, 35.00),
       (24, 1, 24, 200.0, null, 'ACTIVE', 2, 43.00);
SELECT setval('rental_id_seq', (SELECT max(id) FROM rental));

INSERT INTO image (rental_id, path)
VALUES (1, 'image1'),
       (2, 'image2'),
       (3, 'image3'),
       (4, 'image4'),
       (5, 'image5'),
       (6, 'image6'),
       (7, 'image7'),
       (8, 'image8'),
       (9, 'image9'),
       (10, 'image10'),
       (11, 'image11'),
       (12, 'image12'),
       (13, 'image13'),
       (14, 'image14');
-- SELECT setval('image_id_seq', (SELECT max(id) FROM image));

INSERT INTO rental_details (id, rental_id, property_type, parking_type, furnished, year_built, lease_term, short_term,
                            pet_friendly)
VALUES (1, 1, 'APARTMENT', 'NO_PARKING', 'NO_FURNITURE', '2024-10-08', 'MONTHLY', true, true),
       (2, 2, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (3, 3, 'ROOM', 'DRIVEWAY', 'BEDROOM', '2025-10-08', 'MONTHLY', true, true),
       (4, 4, 'CONDO', 'UNDERGROUND', 'ALL', '2024-10-08', 'MONTHLY', true, true),
       (5, 5, 'APARTMENT', 'STREET', 'NO_FURNITURE', '2024-10-08', 'MONTHLY', true, true),
       (6, 6, 'HOUSE', 'NO_PARKING', 'KITCHEN', '2025-10-08', 'MONTHLY', true, true),
       (7, 7, 'ROOM', 'GARAGE', 'ALL', '2024-10-08', 'MONTHLY', true, true),
       (8, 8, 'CONDO', 'DRIVEWAY', 'NO_FURNITURE', '2024-10-08', 'MONTHLY', true, true),
       (9, 9, 'APARTMENT', 'UNDERGROUND', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (10, 10, 'HOUSE', 'STREET', 'BEDROOM', '2024-10-08', 'MONTHLY', true, true),
       (11, 11, 'ROOM', 'NO_PARKING', 'ALL', '2024-10-08', 'MONTHLY', true, true),
       (12, 12, 'CONDO', 'GARAGE', 'NO_FURNITURE', '2024-10-08', 'MONTHLY', true, true),
       (13, 13, 'APARTMENT', 'DRIVEWAY', 'KITCHEN', '2025-10-08', 'MONTHLY', true, true),
       (14, 14, 'HOUSE', 'UNDERGROUND', 'BEDROOM', '2024-10-08', 'MONTHLY', true, true),
       (18, 18, 'APARTMENT', 'NO_PARKING', 'NO_FURNITURE', '2024-10-08', 'MONTHLY', true, true),
       (19, 19, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (20, 20, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (21, 21, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (22, 22, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (23, 23, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true),
       (24, 24, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', 'MONTHLY', true, true);
SELECT setval('rental_details_id_seq', (SELECT max(id) FROM rental_details));

INSERT INTO amenities (rental_id, name)
VALUES (1, 'BARS'),
       (2, 'CAFE'),
       (3, 'SHOPPING_CENTRE'),
       (4, 'BARS'),
       (5, 'BARS'),
       (6, 'BARS'),
       (7, 'DOG_PARK'),
       (8, 'BARS'),
       (9, 'BARS'),
       (10, 'GROCERY_STORE'),
       (11, 'BARS'),
       (12, 'BARS'),
       (13, 'BARS'),
       (14, 'BARS'),
       (1, 'HOSPITAL'),
       (2, 'DOG_PARK'),
       (3, 'SCHOOL'),
       (4, 'GYM'),
       (5, 'DOG_PARK'),
       (6, 'PUBLIC_LIBRARY'),
       (7, 'GROCERY_STORE'),
       (8, 'HOSPITAL'),
       (9, 'SHOPPING_CENTRE'),
       (10, 'BARS'),
       (11, 'SCHOOL'),
       (12, 'GROCERY_STORE'),
       (13, 'CAFE'),
       (14, 'GYM'),
       (1, 'CAFE'),
       (2, 'BARS'),
       (3, 'ATM'),
       (4, 'CAFE'),
       (5, 'BUS_STOP'),
       (6, 'POOL'),
       (7, 'HOSPITAL'),
       (8, 'PARKS'),
       (9, 'CAFE'),
       (10, 'ATM'),
       (11, 'HOSPITAL'),
       (12, 'GYM'),
       (13, 'DOG_PARK'),
       (14, 'ATM'),
       (1, 'ATM');

INSERT INTO feature(rental_id, name)
VALUES (1, 'BIKE_ROOM'),
       (2, 'MOVIE_ROOM'),
       (3, 'ELECTRIC_VEHICLE_CHARGER'),
       (4, 'BIKE_ROOM'),
       (5, 'BIKE_ROOM'),
       (6, 'FITNESS_AREA'),
       (7, 'BIKE_ROOM'),
       (8, 'FITNESS_AREA'),
       (9, 'VIDEO_SURVEILLANCE'),
       (10, 'MOVIE_ROOM'),
       (11, 'BIKE_ROOM'),
       (12, 'ELECTRIC_VEHICLE_CHARGER'),
       (13, 'SECURITY_24H'),
       (14, 'BIKE_ROOM'),
       (1, 'ELECTRIC_VEHICLE_CHARGER'),
       (2, 'SAUNA'),
       (3, 'SAUNA'),
       (4, 'SWIMMING_POOL'),
       (5, 'FITNESS_AREA'),
       (6, 'BIKE_ROOM'),
       (7, 'SECURITY_24H'),
       (8, 'SAUNA'),
       (9, 'SECURITY_24H'),
       (10, 'SWIMMING_POOL'),
       (11, 'SWIMMING_POOL'),
       (12, 'BIKE_ROOM'),
       (13, 'SWIMMING_POOL'),
       (14, 'MOVIE_ROOM'),
       (1, 'FITNESS_AREA'),
       (2, 'SECURITY_24H'),
       (3, 'VIDEO_SURVEILLANCE'),
       (4, 'ELECTRIC_VEHICLE_CHARGER'),
       (5, 'SWIMMING_POOL'),
       (6, 'SAUNA'),
       (7, 'VIDEO_SURVEILLANCE'),
       (8, 'ELECTRIC_VEHICLE_CHARGER'),
       (9, 'STORAGE_LOCKERS'),
       (10, 'SECURITY_24H'),
       (11, 'MOVIE_ROOM');

INSERT INTO category(rental_details_id, name)
VALUES (1, 'STUDENT_HOUSING'),
       (2, 'CORPORATE_HOUSING'),
       (3, 'STUDENT_HOUSING'),
       (4, 'SENIOR_HOUSING'),
       (5, 'CORPORATE_HOUSING'),
       (6, 'SENIOR_HOUSING'),
       (7, 'STUDENT_HOUSING'),
       (8, 'STUDENT_HOUSING'),
       (9, 'SUBLET'),
       (10, 'STUDENT_HOUSING'),
       (11, 'CORPORATE_HOUSING'),
       (12, 'SENIOR_HOUSING'),
       (13, 'STUDENT_HOUSING'),
       (14, 'CORPORATE_HOUSING');

INSERT INTO utility(rental_details_id, name)
VALUES (1, 'NOT_INCLUDED'),
       (2, 'WATER'),
       (3, 'ELECTRICITY'),
       (4, 'SATELLITE_TV'),
       (5, 'ELECTRICITY'),
       (6, 'WATER'),
       (7, 'SATELLITE_TV'),
       (8, 'NOT_INCLUDED'),
       (9, 'INTERNET'),
       (10, 'NOT_INCLUDED'),
       (11, 'SATELLITE_TV'),
       (12, 'WATER'),
       (13, 'NOT_INCLUDED'),
       (14, 'ELECTRICITY');