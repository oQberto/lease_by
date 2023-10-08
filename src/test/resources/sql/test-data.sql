INSERT INTO users (email, username, password)
VALUES ('user1@gmail.com', 'username1', '1231'),
       ('user2@gmail.com', 'username2', '1232'),
       ('user3@gmail.com', 'username3', '1233'),
       ('user4@gmail.com', 'username4', '1234'),
       ('user5@gmail.com', 'username5', '1235'),
       ('user6@gmail.com', 'username6', '1236'),
       ('user7@gmail.com', 'username7', '1237'),
       ('user8@gmail.com', 'username8', '1238'),
       ('user9@gmail.com', 'username9', '1239'),
       ('user10@gmail.com', 'username10', '12310'),
       ('user11@gmail.com', 'username11', '12311'),
       ('user12@gmail.com', 'username12', '12312'),
       ('user13@gmail.com', 'username13', '12313'),
       ('user14@gmail.com', 'username14', '12314'),
       ('user15@gmail.com', 'username15', '12315');

INSERT INTO profile (user_id, avatar_img, first_name, last_name, phone_number)
VALUES (1, 'avatar1', 'firstname1', 'lastname1', '(29)123-4567'),
       (2, 'avatar2', 'firstname2', 'lastname2', '(44)123-4567'),
       (3, 'avatar3', 'firstname3', 'lastname3', '(17)123-4567'),
       (4, 'avatar4', 'firstname4', 'lastname4', '(44)123-4567'),
       (5, 'avatar5', 'firstname5', 'lastname5', '(29)123-4567'),
       (6, 'avatar6', 'firstname6', 'lastname6', '(44)123-4567'),
       (7, 'avatar7', 'firstname7', 'lastname7', '(17)123-4567'),
       (8, 'avatar8', 'firstname8', 'lastname8', '(44)123-4567'),
       (9, 'avatar9', 'firstname9', 'lastname9', '(29)123-4567'),
       (10, 'avatar10', 'firstname10', 'lastname10', '(44)123-4567'),
       (11, 'avatar11', 'firstname11', 'lastname11', '(17)123-4567'),
       (12, 'avatar12', 'firstname12', 'lastname12', '(44)123-4567'),
       (13, 'avatar13', 'firstname13', 'lastname13', '(29)123-4567'),
       (14, 'avatar14', 'firstname14', 'lastname14', '(44)123-4567'),
       (15, 'avatar15', 'firstname15', 'lastname15', '(17)123-4567');

INSERT INTO city (name, image)
VALUES ('Minsk', 'minskImage'),
       ('Grodno', 'grodnoImage'),
       ('Brest', 'brestImage'),
       ('Gomel', 'gomelImage'),
       ('Mogilev', 'mogilevImage'),
       ('Vitebsk', 'vitebskImage');

INSERT INTO street (name, zip_code)
VALUES ('Дружная Улица', '220056'),
       ('Калядная Улица', '220057'),
       ('Солнечная Улица', '220058'),
       ('Улица 40 Лет Победы', '220059'),
       ('Белорусская Улица', '220045'),
       ('Боровая Улица', '2200567'),
       ('Логойская Улица', '220095'),
       ('Славянский Переулок', '220005'),
       ('Улица Пирогова', '220053'),
       ('Янтарная Улица', '220052'),
       ('Верхняя Луговая Улица', '220051'),
       ('Нижняя Боровая Улица', '220050'),
       ('Липовая Улица', '220049'),
       ('Улица Щорса', '220032'),
       ('Сапфировая Улица', '220023'),
       ('Улица Бабушкина', '220098'),
       ('Улица Макаренко', '220001'),
       ('1-Й Дальний Переулок', '220000'),
       ('1-Й Жирмунский Переулок', '220000'),
       ('1-Й Переулок Ивлиева', '220000'),
       ('1-Й Тихий Переулок', '220000'),
       ('1-Я Трудовая Улица', '220000'),
       ('2-Й Жирмунский Переулок', '220000'),
       ('3-Й Каравайный Переулок', '220000'),
       ('3-Й Переулок Серафимовича', '220000'),
       ('Алитусский Переулок', '220000'),
       ('Асфальтная Улица', '220000'),
       ('Аульская Улица', '220000'),
       ('Беловежская Улица', '220000'),
       ('Белостокская Улица', '220000'),
       ('Блакитный Переулок', '220000'),
       ('Ближняя Улица', '220000'),
       ('Бобровый Переулок', '220000');

INSERT INTO address (city_id, street_id, house_no)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5),
       (6, 6, 6),
       (6, 7, 7),
       (5, 8, 8),
       (4, 9, 9),
       (3, 10, 10),
       (2, 11, 11),
       (1, 12, 12),
       (2, 13, 13),
       (3, 14, 14);

INSERT INTO rental (user_id, address_id, price, description)
VALUES (1, 1, 100.0, null),
       (2, 2, 101.0, null),
       (3, 3, 102.0, null),
       (4, 4, 103.0, null),
       (5, 5, 104.0, null),
       (6, 6, 105.0, null),
       (7, 7, 106.0, null),
       (8, 8, 107.0, null),
       (9, 9, 108.0, null),
       (10, 10, 109.0, null),
       (11, 11, 110.0, null),
       (12, 12, 111.0, null),
       (13, 13, 112.0, null),
       (14, 14, 113.0, null);

-- image table
-- INSERT INTO image (rental_id, image_path) VALUES ();

INSERT INTO about (rental_id, property_type, parking_type, furnished, lease_term, short_term, year_built)
VALUES (1, 'APARTMENT', 'NO_PARKING', 'NO_FURNITURE', '2024-10-08', null, '2015-01-01'),
       (2, 'HOUSE', 'GARAGE', 'KITCHEN', '2024-10-08', null, '2018-01-01'),
       (3, 'ROOM', 'DRIVEWAY', 'BEDROOM', '2025-10-08', null, '2012-01-01'),
       (4, 'CONDO', 'UNDERGROUND', 'ALL', '2024-10-08', null, '2015-01-01'),
       (5, 'APARTMENT', 'STREET', 'NO_FURNITURE', '2024-10-08', null, '2018-01-01'),
       (6, 'HOUSE', 'NO_PARKING', 'KITCHEN', '2025-10-08', null, '2015-01-01'),
       (7, 'ROOM', 'GARAGE', 'ALL', '2024-10-08', null, '2015-01-01'),
       (8, 'CONDO', 'DRIVEWAY', 'NO_FURNITURE', '2024-10-08', null, '2015-01-01'),
       (9, 'APARTMENT', 'UNDERGROUND', 'KITCHEN', '2024-10-08', null, '2012-01-01'),
       (10, 'HOUSE', 'STREET', 'BEDROOM', '2024-10-08', null, '2015-01-01'),
       (11, 'ROOM', 'NO_PARKING', 'ALL', '2024-10-08', null, '2015-01-01'),
       (12, 'CONDO', 'GARAGE', 'NO_FURNITURE', '2024-10-08', null, '2012-01-01'),
       (13, 'APARTMENT', 'DRIVEWAY', 'KITCHEN', '2025-10-08', null, '2015-01-01'),
       (14, 'HOUSE', 'UNDERGROUND', 'BEDROOM', '2024-10-08', null, '2018-01-01');

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


