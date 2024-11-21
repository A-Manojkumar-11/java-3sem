CREATE DATABASE transport_db;

USE transport_db;

CREATE TABLE transportation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(10) NOT NULL,
    driver_name VARCHAR(50) NOT NULL,
    route VARCHAR(100) NOT NULL,
    departure_time TIME,
    arrival_time TIME
);
