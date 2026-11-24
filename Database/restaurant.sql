USE universal_booking;

CREATE TABLE restaurantdetails (
    restaurant_id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_name VARCHAR(100) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    rating DECIMAL(2,1) DEFAULT 0.0,
    cuisine VARCHAR(100),
    description VARCHAR(500)
);

INSERT INTO restaurantdetails
(restaurant_name, destination, address, rating, cuisine, description)
VALUES
('The Coastal Table','Mumbai','Marine Drive, Mumbai',4.6,'Indian, Seafood','Premium restaurant with a sea view.'),
('Spice Route','Mumbai','Bandra West, Mumbai',4.5,'Indian, Asian','Modern dining experience with Indian and Asian cuisine.'),
('Royal Tadka','Pune','Koregaon Park, Pune',4.4,'Indian, North Indian','Traditional Indian restaurant with premium dining.'),
('Urban Fork','Pune','Viman Nagar, Pune',4.3,'Continental, Italian','Contemporary restaurant with a wide menu.'),
('Delhi Darbar','Delhi','Connaught Place, Delhi',4.5,'Mughlai, North Indian','Popular restaurant serving traditional Mughlai cuisine.'),
('Capital Kitchen','Delhi','Saket, Delhi',4.4,'Indian, Continental','Fine dining restaurant in South Delhi.'),
('Southern Spice','Chennai','T Nagar, Chennai',4.6,'South Indian','Authentic South Indian dining experience.'),
('Madras Table','Chennai','Adyar, Chennai',4.3,'South Indian, Seafood','Casual restaurant serving local specialties.'),
('Coorg Kitchen','Madikeri','Main Road, Madikeri',4.7,'Coorgi, Indian','Local Coorg cuisine in a comfortable setting.'),
('The Hillside Dine','Madikeri','Abbey Road, Madikeri',4.5,'Indian, Continental','Scenic restaurant with a relaxed atmosphere.');

CREATE TABLE restauranttables (
    table_id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_id INT NOT NULL,
    table_number VARCHAR(10) NOT NULL,
    table_type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price_per_hour DECIMAL(10,2) NOT NULL,
    availability ENUM('Available','Booked') NOT NULL DEFAULT 'Available',

    FOREIGN KEY (restaurant_id)
        REFERENCES restaurantdetails(restaurant_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO restauranttables
(
    restaurant_id,
    table_number,
    table_type,
    capacity,
    price_per_hour,
    availability
)
SELECT
    r.restaurant_id,
    CONCAT(s.section_num,'0',t.table_num) AS table_number,

    CASE
        WHEN s.section_num=2 AND t.table_num>=4
            THEN 'Private Table'
        WHEN s.section_num=2
            THEN 'Premium Table'
        ELSE 'Standard Table'
    END AS table_type,

    CASE
        WHEN s.section_num=2 AND t.table_num>=4
            THEN 8
        WHEN s.section_num=2
            THEN 6
        ELSE 5
    END AS capacity,

    CASE
        WHEN s.section_num=2 AND t.table_num>=4
            THEN 1200
        WHEN s.section_num=2
            THEN 900
        ELSE 500
    END AS price_per_hour,

    'Available' AS availability

FROM restaurantdetails r

CROSS JOIN (
    SELECT 1 AS section_num
    UNION ALL SELECT 2
) s

CROSS JOIN (
    SELECT 1 AS table_num
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
) t;

Select * from restauranttables;


use universal_booking;

CREATE TABLE restaurantbookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_id INT NOT NULL,
    table_id INT NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    guests INT NOT NULL,
    price_per_hour DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    booking_status ENUM('CONFIRMED','CANCELLED') DEFAULT 'CONFIRMED',

    FOREIGN KEY (restaurant_id)
        REFERENCES restaurantdetails(restaurant_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (table_id)
        REFERENCES restauranttables(table_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);