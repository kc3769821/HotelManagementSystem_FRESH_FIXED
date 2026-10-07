-- Hotel Management System - MySQL setup
-- Run this script in MySQL Workbench as root/admin.

CREATE DATABASE IF NOT EXISTS hotel_management
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'hotel_app'@'localhost' IDENTIFIED BY 'hotel123';
ALTER USER 'hotel_app'@'localhost' IDENTIFIED WITH mysql_native_password BY 'hotel123';
GRANT ALL PRIVILEGES ON hotel_management.* TO 'hotel_app'@'localhost';

-- Also allow the same application credentials when MySQL resolves the host as %.
CREATE USER IF NOT EXISTS 'hotel_app'@'%' IDENTIFIED BY 'hotel123';
ALTER USER 'hotel_app'@'%' IDENTIFIED WITH mysql_native_password BY 'hotel123';
GRANT ALL PRIVILEGES ON hotel_management.* TO 'hotel_app'@'%';

FLUSH PRIVILEGES;

USE hotel_management;
SHOW TABLES;
