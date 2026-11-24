use universal_booking;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE transaction;
TRUNCATE TABLE bookings;
TRUNCATE TABLE wallet;
TRUNCATE TABLE users;

UPDATE busseats SET status = 'AVAILABLE';
UPDATE flightseats SET status = 'AVAILABLE';
UPDATE trainseats SET status = 'AVAILABLE';
UPDATE hotelrooms4 SET availability = 'Available';
UPDATE movie_bookings SET status = 'AVAILABLE';
UPDATE restauranttables SET availability = 'Available';

ALTER TABLE bookings AUTO_INCREMENT = 1000;
ALTER TABLE users AUTO_INCREMENT = 1000;
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

SET FOREIGN_KEY_CHECKS = 1;

Select * from users