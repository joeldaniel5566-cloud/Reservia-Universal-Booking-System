use universal_booking;

CREATE TABLE flightdetails (
    flight_number VARCHAR(10) PRIMARY KEY,
    airline_name VARCHAR(100) NOT NULL,
    source_city VARCHAR(50) NOT NULL,
    source_country VARCHAR(50) NOT NULL,
    destination_city VARCHAR(50) NOT NULL,
    destination_country VARCHAR(50) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price INT NOT NULL
);

CREATE TABLE flightseats (
    flight_number VARCHAR(10) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,  -- E.g., '12A', '14F'
    seat_class VARCHAR(20) NOT NULL,   -- ECONOMY, BUSINESS
    seat_type VARCHAR(20) NOT NULL,    -- WINDOW, MIDDLE, AISLE
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    PRIMARY KEY (flight_number, seat_number),
    FOREIGN KEY (flight_number) REFERENCES flightdetails(flight_number) ON DELETE CASCADE
);

INSERT INTO flightdetails VALUES
('AI-131', 'Air India', 'Mumbai', 'India', 'London', 'United Kingdom', '06:30:00', '11:30:00', 45000),
('EK-501', 'Emirates', 'Mumbai', 'India', 'Dubai', 'UAE', '14:00:00', '15:45:00', 18000),
('SQ-421', 'Singapore Airlines', 'Mumbai', 'India', 'Singapore', 'Singapore', '11:45:00', '19:35:00', 28000),
('QF-124', 'Qantas', 'Mumbai', 'India', 'Sydney', 'Australia', '20:10:00', '10:50:00', 62000),

('EK-502', 'Emirates', 'Dubai', 'UAE', 'Mumbai', 'India', '09:10:00', '13:55:00', 19500),
('BA-107', 'British Airways', 'London', 'United Kingdom', 'Mumbai', 'India', '12:40:00', '00:30:00', 48000),
('SQ-307', 'Singapore Airlines', 'London', 'United Kingdom', 'Singapore', 'Singapore', '20:15:00', '16:15:00', 55000),
('QF-002', 'Qantas', 'London', 'United Kingdom', 'Sydney', 'Australia', '21:30:00', '05:15:00', 98000),

('SQ-422', 'Singapore Airlines', 'Singapore', 'Singapore', 'Mumbai', 'India', '07:55:00', '11:00:00', 26500),
('BA-011', 'British Airways', 'Singapore', 'Singapore', 'London', 'United Kingdom', '23:20:00', '05:55:00', 52000),
('EK-355', 'Emirates', 'Singapore', 'Singapore', 'Dubai', 'UAE', '21:00:00', '00:20:00', 24000),
('QF-082', 'Qantas', 'Singapore', 'Singapore', 'Sydney', 'Australia', '20:30:00', '06:10:00', 38000),

('QF-123', 'Qantas', 'Sydney', 'Australia', 'Mumbai', 'India', '10:15:00', '22:00:00', 65000),
('BA-016', 'British Airways', 'Sydney', 'Australia', 'London', 'United Kingdom', '15:45:00', '05:10:00', 95000),
('EK-413', 'Emirates', 'Sydney', 'Australia', 'Dubai', 'UAE', '21:45:00', '05:15:00', 58000),
('SQ-232', 'Singapore Airlines', 'Sydney', 'Australia', 'Singapore', 'Singapore', '12:15:00', '17:40:00', 41000),

('EK-001', 'Emirates', 'Dubai', 'UAE', 'London', 'United Kingdom', '07:45:00', '12:25:00', 32000),
('SQ-495', 'Singapore Airlines', 'Dubai', 'UAE', 'Singapore', 'Singapore', '09:15:00', '20:40:00', 29000),
('QF-042', 'Qantas', 'Dubai', 'UAE', 'Sydney', 'Australia', '10:15:00', '06:00:00', 54000);

INSERT INTO flightseats (flight_number, seat_number, seat_class, seat_type, status)
SELECT 
    f.flight_number,
    CONCAT(r.row_num, c.letter) AS seat_number,
    'ECONOMY' AS seat_class,
    CASE 
        WHEN c.letter IN ('A', 'F') THEN 'WINDOW'
        WHEN c.letter IN ('B', 'E') THEN 'MIDDLE'
        ELSE 'AISLE'
    END AS seat_type,
    'AVAILABLE' AS status
FROM flightdetails f
CROSS JOIN (
    SELECT 1 AS row_num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 
    UNION ALL SELECT 9 UNION ALL SELECT 10
) r
CROSS JOIN (
    SELECT 'A' AS letter UNION ALL SELECT 'B' UNION ALL SELECT 'C' 
    UNION ALL SELECT 'D' UNION ALL SELECT 'E' UNION ALL SELECT 'F'
) c;

Select * from flightseats