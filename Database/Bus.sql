use universal_booking;

CREATE TABLE busdetails (
    source_city VARCHAR(50) NOT NULL,
    destination_city VARCHAR(50) NOT NULL,
    bus_number VARCHAR(20) NOT NULL,
    bus_name VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price INT NOT NULL
);

INSERT INTO busdetails 
    (source_city, destination_city, bus_number, bus_name, departure_time, arrival_time, price) 
VALUES
    
    ('Mumbai', 'Pune', 'MH-14-BT-4521', 'MSRTC SHIVNERI AC', '07:00', '11:30', 575),
    ('Mumbai', 'Pune', 'MH-12-QY-8840', 'MSRTC SHIVSHAHI', '14:30', '19:15', 380),
   
    ('Mumbai', 'Nashik', 'MH-15-BD-2211', 'MSRTC SHIVNERI AC', '08:00', '13:00', 520),
    ('Mumbai', 'Nashik', 'MH-43-FI-9050', 'MSRTC SHIVSHAHI', '13:15', '18:30', 340),
  
    ('Mumbai', 'Kolhapur', 'MH-09-FL-8899', 'MSRTC SHIVSHAHI SLEEPER', '21:00', '06:30', 780),
    ('Mumbai', 'Kolhapur', 'MH-14-CP-2233', 'MSRTC HIRKANI', '08:45', '18:15', 520),

    ('Mumbai', 'Aurangabad', 'MH-20-EA-4141', 'MSRTC SHIVSHAHI', '09:00', '17:30', 650),
    ('Mumbai', 'Aurangabad', 'MH-20-ZA-8899', 'MSRTC ORDINARY EXP', '22:15', '06:45', 410),

   
    ('Pune', 'Mumbai', 'MH-14-BT-4522', 'MSRTC SHIVNERI AC', '06:00', '10:30', 575),
    ('Pune', 'Mumbai', 'MH-12-QY-8841', 'MSRTC SHIVSHAHI', '15:45', '20:30', 380),
  
    ('Pune', 'Nashik', 'MH-12-EF-1122', 'MSRTC HIRKANI', '06:15', '12:15', 330),
    ('Pune', 'Nashik', 'MH-15-GH-4455', 'MSRTC SHIVSHAHI', '13:30', '19:00', 440),
   
    ('Pune', 'Kolhapur', 'MH-09-KK-1212', 'MSRTC SHIVSHAHI', '07:30', '13:00', 400),
    ('Pune', 'Kolhapur', 'MH-14-AA-7788', 'MSRTC LALPARI EXP', '16:00', '22:00', 260),

    ('Pune', 'Aurangabad', 'MH-20-DJ-3020', 'MSRTC SHIVSHAHI', '07:30', '12:45', 410),
    ('Pune', 'Aurangabad', 'MH-12-JK-4545', 'MSRTC LALPARI EXP', '23:00', '04:45', 240),

    
    ('Nashik', 'Mumbai', 'MH-15-BD-2212', 'MSRTC SHIVNERI AC', '06:30', '11:30', 520),
    ('Nashik', 'Mumbai', 'MH-43-FI-9051', 'MSRTC SHIVSHAHI', '14:00', '19:15', 340),
   
    ('Nashik', 'Pune', 'MH-12-EF-1123', 'MSRTC HIRKANI', '05:45', '11:45', 330),
    ('Nashik', 'Pune', 'MH-15-GH-4456', 'MSRTC SHIVSHAHI', '15:00', '20:30', 440),

    ('Nashik', 'Kolhapur', 'MH-09-UU-9900', 'MSRTC SHIVSHAHI SLEEPER', '19:00', '06:30', 920),

    ('Nashik', 'Aurangabad', 'MH-20-BB-3344', 'MSRTC HIRKANI', '08:30', '13:00', 250),
    ('Nashik', 'Aurangabad', 'MH-15-CC-5566', 'MSRTC LALPARI EXP', '14:15', '19:00', 180),


    ('Kolhapur', 'Mumbai', 'MH-09-FL-8900', 'MSRTC SHIVSHAHI SLEEPER', '20:30', '06:00', 780),
    ('Kolhapur', 'Mumbai', 'MH-43-GH-6678', 'MSRTC LALPARI EXP', '21:45', '08:15', 430),

    ('Kolhapur', 'Pune', 'MH-09-KK-1213', 'MSRTC SHIVSHAHI', '05:30', '11:00', 400),
    ('Kolhapur', 'Pune', 'MH-14-AA-7789', 'MSRTC LALPARI EXP', '13:15', '19:15', 260),

    ('Kolhapur', 'Nashik', 'MH-09-UU-9901', 'MSRTC SHIVSHAHI SLEEPER', '18:30', '06:00', 920),

    ('Kolhapur', 'Aurangabad', 'MH-20-LL-5566', 'MSRTC SHIVSHAHI SLEEPER', '20:00', '07:30', 880),

    ('Aurangabad', 'Mumbai', 'MH-20-EA-4142', 'MSRTC SHIVSHAHI', '06:30', '15:00', 650),
    ('Aurangabad', 'Mumbai', 'MH-20-ZA-8900', 'MSRTC ORDINARY EXP', '21:00', '05:30', 410),

    ('Aurangabad', 'Pune', 'MH-20-DJ-3021', 'MSRTC SHIVSHAHI', '06:00', '11:15', 410),
    ('Aurangabad', 'Pune', 'MH-12-JK-4546', 'MSRTC LALPARI EXP', '22:15', '04:00', 240),

    ('Aurangabad', 'Nashik', 'MH-20-BB-3345', 'MSRTC HIRKANI', '07:00', '11:30', 250),
    ('Aurangabad', 'Nashik', 'MH-15-CC-5567', 'MSRTC LALPARI EXP', '16:30', '21:15', 180),

    ('Aurangabad', 'Kolhapur', 'MH-20-LL-5567', 'MSRTC SHIVSHAHI SLEEPER', '19:30', '07:00', 880);


SELECT * FROM busdetails;

drop table busdetails;

CREATE TABLE busseats (
    bus_number VARCHAR(20) NOT NULL,
    seat_number INT NOT NULL,
    seat_type VARCHAR(20) NOT NULL, -- WINDOW, AISLE
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    PRIMARY KEY (bus_number, seat_number)
);

INSERT INTO busseats (bus_number, seat_number, seat_type, status)
SELECT 
    b.bus_number,
    s.seat_number,
    CASE
        -- Pattern for a 2x2 bus layout where outer seats are windows
        WHEN MOD(s.seat_number, 4) = 1 OR MOD(s.seat_number, 4) = 0 THEN 'WINDOW'
        ELSE 'AISLE'
    END,
    'AVAILABLE'
FROM busdetails b
CROSS JOIN (
    SELECT 1 AS seat_number UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 
    UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 
    UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 
    UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20 
    UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 
    UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 
    UNION ALL SELECT 29 UNION ALL SELECT 30
) s
WHERE NOT EXISTS (
    SELECT 1
    FROM busseats bs
    WHERE bs.bus_number = b.bus_number
);

-- View generated layout
SELECT * FROM busseats;