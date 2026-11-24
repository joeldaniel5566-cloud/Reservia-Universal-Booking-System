use universal_booking;

CREATE TABLE hoteldetails5 (
    hotel_id INT PRIMARY KEY AUTO_INCREMENT,
    hotel_name VARCHAR(100) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    address VARCHAR(200),
    rating DECIMAL(2,1),
    description VARCHAR(500)
);

INSERT INTO hoteldetails5
(hotel_name, destination, address, rating, description)
VALUES
('Taj Mahal Palace', 'Mumbai',
 'Apollo Bunder, Colaba, Mumbai', 5.0,
 'Luxury hotel overlooking the Gateway of India'),

('The Leela Mumbai', 'Mumbai',
 'Andheri East, Mumbai', 4.7,
 'Luxury hotel with modern rooms and excellent facilities'),

('JW Marriott Mumbai', 'Mumbai',
 'Juhu Tara Road, Mumbai', 4.8,
 'Premium hotel near Juhu Beach'),

('The Westin Pune', 'Pune',
 'Koregaon Park, Pune', 4.6,
 'Luxury hotel with spacious rooms and modern amenities'),

('Hyatt Regency Pune', 'Pune',
 'Viman Nagar, Pune', 4.5,
 'Elegant hotel close to Pune airport'),

('The Lalit New Delhi', 'Delhi',
 'Barakhamba Avenue, New Delhi', 4.6,
 'Luxury hotel in central Delhi'),

('ITC Maurya', 'Delhi',
 'Sardar Patel Marg, New Delhi', 4.9,
 'Premium luxury hotel in New Delhi'),

('ITC Grand Chola', 'Chennai',
 'Mount Road, Chennai', 4.8,
 'Luxury hotel with multiple dining and recreation facilities'),

('Taj Coromandel', 'Chennai',
 'Nungambakkam, Chennai', 4.7,
 'Luxury hotel in the heart of Chennai'),

('Taj Madikeri Resort', 'Madikeri',
 'Coorg, Karnataka', 4.6,
 'Luxury resort surrounded by nature');

CREATE TABLE hotelrooms4 (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    hotel_id INT NOT NULL,
    room_number VARCHAR(10) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    availability ENUM('Available', 'Booked') NOT NULL DEFAULT 'Available',

    FOREIGN KEY (hotel_id)
        REFERENCES hoteldetails5(hotel_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO hotelrooms4
(
    hotel_id,
    room_number,
    room_type,
    capacity,
    price_per_night,
    availability
)

SELECT
    h.hotel_id,

    CONCAT(f.floor_num, '0', r.room_num) AS room_number,

    CASE
        WHEN f.floor_num = 4 AND r.room_num >= 4
            THEN 'Presidential Suite'

        WHEN f.floor_num = 4
            THEN 'Executive Suite'

        WHEN f.floor_num = 3
            THEN 'Executive Room'

        WHEN f.floor_num = 2
            THEN 'Deluxe Room'

        ELSE 'Standard Room'
    END AS room_type,

    CASE
        WHEN f.floor_num = 4 AND r.room_num >= 4
            THEN 5

        WHEN f.floor_num = 4
            THEN 4

        WHEN f.floor_num = 3
            THEN 3

        ELSE 2
    END AS capacity,

    CASE
        WHEN f.floor_num = 4 AND r.room_num >= 4
            THEN 20000

        WHEN f.floor_num = 4
            THEN 14000

        WHEN f.floor_num = 3
            THEN 9000

        WHEN f.floor_num = 2
            THEN 6500

        ELSE 5000
    END AS price_per_night,

    'Available' AS availability

FROM hoteldetails5 h

CROSS JOIN
(
    SELECT 1 AS floor_num
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
) f

CROSS JOIN
(
    SELECT 1 AS room_num
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
) r;

Select * from hotelrooms3;
Select * from hoteldetails5;