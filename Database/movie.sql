use universal_booking;

CREATE TABLE movie_bookings (
    movie_name VARCHAR(100) NOT NULL,
    movie_type VARCHAR(50) NOT NULL,
    rating VARCHAR(10) NOT NULL,
    start_time VARCHAR(20) NOT NULL,
    duration VARCHAR(20) NOT NULL,
    theatre_name VARCHAR(100) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_type VARCHAR(20) NOT NULL,   
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    PRIMARY KEY (movie_name, seat_number) -- Prevents double-booking same seat for the movie
);

INSERT INTO movie_bookings (movie_name, movie_type, rating, start_time, duration, theatre_name, seat_number, seat_type, status)
SELECT 
    m.movie_name,
    m.movie_type,
    m.rating,
    m.start_time,
    m.duration,
    m.theatre_name,
    CONCAT(r.row_letter, n.seat_num) AS seat_number,
    CASE 
        WHEN r.row_letter IN ('I', 'J') THEN 'RECLINER'
        WHEN r.row_letter IN ('F', 'G', 'H') THEN 'PREMIUM'
        ELSE 'NORMAL'
    END AS seat_type,
    'AVAILABLE' AS status
FROM (
    
    SELECT 'Dhurandhar' AS movie_name, 'Action Thriller' AS movie_type, '8.2' AS rating, '4:00 PM' AS start_time, '4h 10m' AS duration, 'PVR INOX' AS theatre_name
    UNION ALL SELECT 'Cocktail', 'Romantic Comedy', '6.6', '11:00 AM', '2h 50m', 'Metro'
    UNION ALL SELECT 'Alpha', 'Action Thriller', '3.0', '1:00 PM', '2h 20m', 'Cinepolis Mall'
    UNION ALL SELECT 'The Mask', 'Action Comedy', '7.0', '8:00 PM', '1h 41m', 'Mukta A2'
    UNION ALL SELECT 'Michael', 'Drama Musical', '7.4', '10:00 PM', '2h 7m', 'Regal Cinema'
    UNION ALL SELECT 'Dhamaal 4', 'Comedy', '8.1', '8:00 AM', '2h 23m', 'G7 Multiplex'
    UNION ALL SELECT 'Bhoot Bangla', 'Comedy Horror', '5.0', '8:00 PM', '2h 44m', 'Chitra Cinema'
    UNION ALL SELECT 'Spiderman', 'Action Adventure', '8.0', '10:00 AM', '2h 30m', 'Liberty Cinema'
    UNION ALL SELECT 'Samarth', 'Drama Devotional', '8.8', '8:00 AM', '2h 26m', 'G7 Multiplex'
    UNION ALL SELECT 'Mirzapur', 'Gangster Thriller', 'A', '8:00 PM', '3h 17m', 'Chitra Cinema'
) m
CROSS JOIN (
   
    SELECT 'A' AS row_letter UNION ALL SELECT 'B' UNION ALL SELECT 'C' UNION ALL SELECT 'D'
    UNION ALL SELECT 'E' UNION ALL SELECT 'F' UNION ALL SELECT 'G' UNION ALL SELECT 'H'
    UNION ALL SELECT 'I' UNION ALL SELECT 'J'
) r
CROSS JOIN (
   
    SELECT 1 AS seat_num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
    UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10
    UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15
) n;

Select * from movie_bookings;