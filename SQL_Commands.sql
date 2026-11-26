-- SQL Database Schema

-- Step 1: Create the database
CREATE DATABASE booking_system;

-- Step 2: Use the database
USE booking_system;

-- Step 3: Create tables
CREATE TABLE Customer (
                          customer_id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          type ENUM('COMPANY', 'INDIVIDUAL') NOT NULL,
                          contact_info VARCHAR(255) NOT NULL
);

CREATE TABLE Classroom (
                           room_id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           capacity INT NOT NULL,
                           equipment TEXT, -- Comma-separated list of equipment
                           isAccessible BOOLEAN DEFAULT FALSE
);

CREATE TABLE Booking (
                         booking_id INT PRIMARY KEY AUTO_INCREMENT,
                         customer_id INT NOT NULL,
                         classroom_id INT NOT NULL,
                         date DATE NOT NULL,
                         start_time TIME NOT NULL,
                         end_time TIME NOT NULL,
                         comments TEXT,
                         FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                         FOREIGN KEY (classroom_id) REFERENCES Classroom(room_id)
);

-- Step 4: Add indexes for performance
CREATE INDEX idx_booking_date ON Booking(date);
CREATE INDEX idx_booking_time ON Booking(start_time, end_time);
CREATE INDEX idx_classroom_equipment ON Classroom(equipment(255)); -- Specify key length
CREATE INDEX idx_classroom_isAccessible ON Classroom(isAccessible);

-- Key SQL Queries for Requirements

-- 1. Search Rooms by Criteria

SELECT * FROM Classroom
WHERE
    (capacity >= ?) AND
    (equipment LIKE ?) AND
    (accessible = ?);

-- 2. Validate Booking (No Double-Booking)

SELECT * FROM Booking
WHERE
    classroom_id = ? AND
    date = ? AND
    (start_time < ? AND end_time > ?);

-- 3. View Upcoming Bookings

SELECT * FROM Booking
WHERE date > CURRENT_DATE;

-- JDBC Example


String SQL = "INSERT INTO Booking (customer_id, classroom_id, date, start_time, end_time, comments) VALUES (?, ?, ?, ?, ?, ?)";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setInt(1, booking.getCustomerId());
stmt.setInt(2, booking.getClassroomId());
stmt.setDate(3, Date.valueOf(booking.getDate()));
stmt.setTime(4, Time.valueOf(booking.getStartTime()));
stmt.setTime(5, Time.valueOf(booking.getEndTime()));
stmt.setString(6, booking.getComments());
stmt.executeUpdate();