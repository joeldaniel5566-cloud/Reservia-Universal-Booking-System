use universal_booking;

CREATE TABLE refund (
    refund_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    booking_id INT NOT NULL,
    refund_amount DECIMAL(20,0) NOT NULL,
    refund_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) DEFAULT 'Refunded'
);

