Create DATABASE universal_boooking;

USE universal_booking;
Create table users(
user_id INT Primary KEY AUTO_INCREMENT,
fullname VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
phone VARCHAR(15),
username VARCHAR(100) NOT NULL,
epassword VARCHAR(100) NOT NULL,
created_at timestamp default
current_timestamp
);

create table transaction(
transaction_id INT auto_increment Primary Key,
user_id INT NOT NUll,
transaction_type varchar(50),
description varchar(100),
booking_id varchar(30),
transaction_date datetime,
amount decimal(10,2),
status varchar(30)
);

create table wallet(
wallet_id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL UNIQUE,
balance NUMERIC(20),
Foreign Key(user_id) references users(user_id) on DELETE CASCADE
);



