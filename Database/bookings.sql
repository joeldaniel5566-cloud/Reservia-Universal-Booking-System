use universal_booking;
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    booking_type VARCHAR(30) NOT NULL,
    booking_name VARCHAR(150) NOT NULL,
    booking_details VARCHAR(255),
    location VARCHAR(255),
    booking_date DATE,
    booking_time TIME,
    amount BIGINT NOT NULL,
    status VARCHAR(30) DEFAULT 'Confirmed',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
) auto_increment = 1000;

describe transaction;
describe busseats;