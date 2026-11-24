USE universal_booking;

CREATE TABLE traindetails (
    source_city VARCHAR(50) NOT NULL,
    destination_city VARCHAR(50) NOT NULL,
    train_number VARCHAR(10) NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price INT NOT NULL
);


INSERT INTO traindetails 
    (source_city, destination_city, train_number, train_name, departure_time, arrival_time, price) 
VALUES
   
    ('Mumbai', 'Delhi', '12951', 'MUMBAI RAJDHANI', '17:00', '08:32', 725),
    ('Mumbai', 'Delhi', '12903', 'GOLDEN TEMPLE M', '18:45', '18:45', 660),
    ('Mumbai', 'Delhi', '12137', 'PUNJAB MAIL', '19:35', '21:45', 710),

    ('Mumbai', 'Pune', '11029', 'KOYNA EXPRESS', '08:40', '12:35', 150),
    ('Mumbai', 'Pune', '11019', 'KONARK EXPRESS', '14:00', '17:55', 150),
    ('Mumbai', 'Pune', '17411', 'MAHALAXMI EXP', '20:15', '00:10', 150),

    ('Mumbai', 'Lucknow', '20103', 'LTT GKP EXPRESS', '17:15', '18:10', 625),
    ('Mumbai', 'Lucknow', '12107', 'LTT SITAPUR EXP', '16:25', '15:40', 630),
    ('Mumbai', 'Lucknow', '12534', 'PUSHPAK EXPRESS', '20:25', '19:10', 640),

    ('Mumbai', 'Chennai', '22159', 'CSMT CHENNAI EXP', '12:45', '10:45', 635),
    ('Mumbai', 'Chennai', '12163', 'LTT MAS EXP', '18:30', '16:10', 630),
    ('Mumbai', 'Chennai', '22157', 'CSMT MS SF MAIL', '22:52', '22:15', 635),

    ('Pune', 'Mumbai', '11030', 'KOYNA EXPRESS', '15:45', '20:15', 150),
    ('Pune', 'Mumbai', '11020', 'KONARK EXPRESS', '23:50', '04:00', 150),
    ('Pune', 'Mumbai', '17412', 'MAHALAXMI EXP', '01:25', '07:25', 150),

    ('Pune', 'Delhi', '22685', 'CDG SKRANTI EXP', '09:00', '11:55', 755),
    ('Pune', 'Delhi', '12779', 'GOA EXPRESS', '04:30', '06:25', 695),
    ('Pune', 'Delhi', '11077', 'JHELUM EXPRESS', '17:20', '23:20', 695),

    ('Pune', 'Lucknow', '12103', 'PUNE LKO SF EXP', '16:15', '17:05', 695),
    ('Pune', 'Lucknow', '11407', 'PUNE LJN EXPRESS', '22:00', '02:40', 665),
    ('Pune', 'Lucknow', '15030', 'PUNE GKP EXPRESS', '11:30', '15:15', 665),

    ('Pune', 'Chennai', '11017', 'LTT KARAIKAL EXP', '16:45', '15:45', 570),
    ('Pune', 'Chennai', '22160', 'MAS CSMT EXP', '11:55', '09:30', 575),
    ('Pune', 'Chennai', '12164', 'MAS LTT SF EXP', '01:40', '23:20', 575),

    ('Delhi', 'Mumbai', '12904', 'GOLDEN TEMPLE M', '04:00', '23:55', 655),
    ('Delhi', 'Mumbai', '12138', 'PUNJAB MAIL', '05:10', '07:35', 710),
    ('Delhi', 'Mumbai', '19020', 'HW BDTS EXP', '18:55', '22:30', 605),

    ('Delhi', 'Pune', '22686', 'YPR SAKRANTI EXP', '22:00', '02:00', 755),
    ('Delhi', 'Pune', '12780', 'GOA EXPRESS', '15:15', '16:00', 695),
    ('Delhi', 'Pune', '11078', 'JHELUM EXPRESS', '11:40', '16:00', 695),

    ('Delhi', 'Lucknow', '12430', 'NDLS LKO AC SF', '23:25', '06:00', 320),
    ('Delhi', 'Lucknow', '14206', 'AYODHYA EXPRESS', '18:20', '03:15', 310),
    ('Delhi', 'Lucknow', '12230', 'LUCKNOW MAIL', '22:00', '06:50', 340),

    ('Delhi', 'Chennai', '12616', 'GRAND TRUNK EXP', '16:10', '04:30', 790),
    ('Delhi', 'Chennai', '12622', 'TAMILNADU EXP', '22:10', '06:15', 810),
    ('Delhi', 'Chennai', '12270', 'NZM MAS DURONTO', '15:55', '20:40', 850),


    ('Chennai', 'Mumbai', '22160', 'MAS CSMT EXP', '13:25', '11:55', 635),
    ('Chennai', 'Mumbai', '12164', 'MAS LTT SF EXP', '18:20', '01:40', 630),
    ('Chennai', 'Mumbai', '22158', 'MS CSMT SF MAIL', '06:20', '05:50', 635),

    ('Chennai', 'Pune', '11018', 'KIK LTT EXPRESS', '22:10', '23:40', 570),
    ('Chennai', 'Pune', '22159', 'CSMT CHENNAI EXP', '10:45', '00:10', 575),
    ('Chennai', 'Pune', '12163', 'LTT MAS EXP', '16:10', '17:25', 575),

    ('Chennai', 'Delhi', '12615', 'GRAND TRUNK EXP', '18:40', '06:30', 790),
    ('Chennai', 'Delhi', '12621', 'TAMILNADU EXP', '22:00', '06:10', 810),
    ('Chennai', 'Delhi', '12269', 'MAS NZM DURONTO', '06:35', '10:30', 850),

    ('Chennai', 'Lucknow', '16093', 'MAS LJN EXPRESS', '05:15', '20:30', 865),
    ('Chennai', 'Lucknow', '12512', 'RAPTISAGAR SF EX', '23:30', '11:45', 865),
    ('Chennai', 'Lucknow', '12522', 'RAPTISAGAR EXP', '23:30', '12:45', 865),

    ('Lucknow', 'Mumbai', '20104', 'GKP LTT EXPRESS', '04:00', '04:35', 625),
    ('Lucknow', 'Mumbai', '12108', 'SITAPUR LTT EXP', '22:20', '21:50', 630),
    ('Lucknow', 'Mumbai', '12533', 'PUSHPAK EXPRESS', '21:30', '20:10', 640),

    ('Lucknow', 'Pune', '12104', 'LUCKNOW PUNE SF', '16:20', '17:50', 695),
    ('Lucknow', 'Pune', '11408', 'LJN PUNE EXP', '06:30', '10:50', 665),
    ('Lucknow', 'Pune', '15029', 'GKP PUNE EXP', '22:50', '02:15', 665),

    ('Lucknow', 'Delhi', '12429', 'LKO NDLS AC SF', '23:30', '06:00', 320),
    ('Lucknow', 'Delhi', '14205', 'AYODHYA EXPRESS', '19:55', '04:20', 310),
    ('Lucknow', 'Delhi', '12229', 'LUCKNOW MAIL', '22:00', '06:55', 340),

    ('Lucknow', 'Chennai', '16094', 'LJN MAS EXPRESS', '16:20', '09:30', 865),
    ('Lucknow', 'Chennai', '12511', 'RAPTISAGAR SF EX', '11:05', '23:30', 865),
    ('Lucknow', 'Chennai', '12521', 'RAPTISAGAR EXP', '11:05', '23:30', 865);

SELECT *
FROM traindetails;

CREATE TABLE trainseats (
seat_id INT AUTO_INCREMENT PRIMARY KEY,
train_number VARCHAR(20) NOT NULL,
coach VARCHAR(10) NOT NULL,
seat_number INT NOT NULL,
seat_type VARCHAR(20),
status VARCHAR(20) DEFAULT 'AVAILABLE');

INSERT INTO trainseats
(train_number, coach, seat_number, seat_type, status)
VALUES
('11029', 'A1', 1, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 2, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 3, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 4, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 5, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 6, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 7, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 8, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 9, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 10, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 11, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 12, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 13, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 14, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 15, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 16, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 17, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 18, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 19, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 20, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 21, 'UPPER', 'AVAILABLE'),
('11029', 'A1', 22, 'LOWER', 'AVAILABLE'),
('11029', 'A1', 23, 'MIDDLE', 'AVAILABLE'),
('11029', 'A1', 24, 'UPPER', 'AVAILABLE');

SELECT * FROM trainseats;

INSERT INTO trainseats (train_number, coach, seat_number, seat_type, status)
SELECT 
    t.train_number,
    'A1',
    s.seat_number,
    CASE
        WHEN MOD(s.seat_number, 3) = 1 THEN 'LOWER'
        WHEN MOD(s.seat_number, 3) = 2 THEN 'MIDDLE'
        ELSE 'UPPER'
    END,
    'AVAILABLE'
FROM traindetails t
CROSS JOIN (
    SELECT 1 AS seat_number
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
    UNION ALL SELECT 6
    UNION ALL SELECT 7
    UNION ALL SELECT 8
    UNION ALL SELECT 9
    UNION ALL SELECT 10
    UNION ALL SELECT 11
    UNION ALL SELECT 12
    UNION ALL SELECT 13
    UNION ALL SELECT 14
    UNION ALL SELECT 15
    UNION ALL SELECT 16
    UNION ALL SELECT 17
    UNION ALL SELECT 18
    UNION ALL SELECT 19
    UNION ALL SELECT 20
    UNION ALL SELECT 21
    UNION ALL SELECT 22
    UNION ALL SELECT 23
    UNION ALL SELECT 24
) s
WHERE NOT EXISTS (
    SELECT 1
    FROM trainseats ts
    WHERE ts.train_number = t.train_number
);