-- Eco-Scholars Database Initialization Script
-- This script creates the database schema and initial data

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS ecoscholars;
USE ecoscholars;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create initial admin user
INSERT IGNORE INTO users (id, username, email, password, first_name, last_name, is_active, is_email_verified, created_at, updated_at) 
VALUES (1, 'admin', 'admin@ecoscholars.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'System', 'Administrator', true, true, NOW(), NOW());

-- Create initial roles for admin user
INSERT IGNORE INTO user_roles (user_id, role) VALUES (1, 'SUPER_ADMIN');

-- Create sample school
INSERT IGNORE INTO schools (id, name, address, city, state, country, pin_code, phone, email, principal_name, registration_number, total_students, total_plants, is_active, created_at, updated_at)
VALUES (1, 'Green Valley School', '123 Nature Street', 'Eco City', 'Green State', 'India', '123456', '+91-9876543210', 'info@greenvalleyschool.edu', 'Dr. Nature Lover', 'SCH001', 0, 0, true, NOW(), NOW());

-- Create sample school admin
INSERT IGNORE INTO school_admins (id, first_name, last_name, email, password, phone, designation, school_id, is_email_verified, is_active, created_at, updated_at)
VALUES (1, 'School', 'Admin', 'admin@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+91-9876543211', 'Principal', 1, true, true, NOW(), NOW());

-- Create sample plants
INSERT IGNORE INTO plants (id, name, scientific_name, species, variety, date_of_plantation, location, soil_type, water_requirements, sunlight_requirements, ecological_importance, current_height, current_width, health_status, school_id, is_active, created_at, updated_at)
VALUES 
(1, 'Neem Tree', 'Azadirachta indica', 'Neem', 'Indian Neem', '2024-01-15', 'School Garden', 'Loamy', 'Moderate', 'Full Sun', 'Air purification, medicinal properties', 2.5, 1.2, 'HEALTHY', 1, true, NOW(), NOW()),
(2, 'Mango Tree', 'Mangifera indica', 'Mango', 'Alphonso', '2024-01-20', 'School Garden', 'Clay', 'Regular', 'Full Sun', 'Fruit production, shade', 3.0, 1.8, 'HEALTHY', 1, true, NOW(), NOW()),
(3, 'Tulsi Plant', 'Ocimum tenuiflorum', 'Tulsi', 'Holy Basil', '2024-02-01', 'School Garden', 'Well-drained', 'Daily', 'Partial Sun', 'Medicinal properties, air purification', 0.8, 0.5, 'HEALTHY', 1, true, NOW(), NOW());

-- Create sample students
INSERT IGNORE INTO students (id, first_name, last_name, email, password, date_of_birth, grade, roll_number, phone, parent_name, parent_phone, parent_email, address, enrollment_date, is_graduated, green_points, is_email_verified, school_id, is_active, created_at, updated_at)
VALUES 
(1, 'Rahul', 'Sharma', 'rahul.sharma@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '2010-05-15', '8th', 'GV001', '+91-9876543212', 'Mr. Rajesh Sharma', '+91-9876543213', 'rajesh.sharma@email.com', '123 Student Lane', '2024-01-01', false, 150, true, 1, true, NOW(), NOW()),
(2, 'Priya', 'Patel', 'priya.patel@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '2010-08-22', '8th', 'GV002', '+91-9876543214', 'Mrs. Sunita Patel', '+91-9876543215', 'sunita.patel@email.com', '456 Student Avenue', '2024-01-01', false, 200, true, 1, true, NOW(), NOW()),
(3, 'Arjun', 'Singh', 'arjun.singh@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '2010-12-10', '7th', 'GV003', '+91-9876543216', 'Mr. Vikram Singh', '+91-9876543217', 'vikram.singh@email.com', '789 Student Street', '2024-01-01', false, 100, true, 1, true, NOW(), NOW());

-- Assign plants to students
UPDATE plants SET assigned_student_id = 1 WHERE id = 1;
UPDATE plants SET assigned_student_id = 2 WHERE id = 2;
UPDATE plants SET assigned_student_id = 3 WHERE id = 3;

-- Create sample growth records
INSERT IGNORE INTO growth_records (id, year, height, width, trunk_diameter, leaf_count, flower_count, fruit_count, health_status, weather_conditions, care_activities, challenges_faced, achievements, notes, photo_url, photo_caption, record_date, is_verified, verified_by, verification_date, plant_id, student_id, is_active, created_at, updated_at)
VALUES 
(1, 2024, 2.5, 1.2, 0.15, 45, 0, 0, 'HEALTHY', 'Sunny and warm', 'Daily watering, weekly fertilizing', 'None', 'Plant is growing well', 'Neem tree is thriving in school garden', 'https://example.com/neem-2024.jpg', 'Neem Tree Growth 2024', '2024-06-15', true, 'School Admin', '2024-06-16', 1, 1, true, NOW(), NOW()),
(2, 2024, 3.0, 1.8, 0.20, 60, 5, 0, 'HEALTHY', 'Sunny and warm', 'Daily watering, monthly fertilizing', 'None', 'First flowers appeared', 'Mango tree flowering for first time', 'https://example.com/mango-2024.jpg', 'Mango Tree Flowers 2024', '2024-06-20', true, 'School Admin', '2024-06-21', 2, 2, true, NOW(), NOW()),
(3, 2024, 0.8, 0.5, 0.05, 25, 0, 0, 'HEALTHY', 'Sunny and warm', 'Daily watering, regular pruning', 'None', 'Plant is healthy and aromatic', 'Tulsi plant growing well', 'https://example.com/tulsi-2024.jpg', 'Tulsi Plant 2024', '2024-06-10', true, 'School Admin', '2024-06-11', 3, 3, true, NOW(), NOW());

-- Create sample achievements
INSERT IGNORE INTO achievements (id, type, title, description, icon_url, points_awarded, badge_color, criteria, is_active, awarded_date, awarded_by, student_id, is_active, created_at, updated_at)
VALUES 
(1, 'POINTS_MILESTONE', 'Green Starter', 'Earned first 100 green points', 'https://example.com/green-starter.png', 100, '#4CAF50', 'Reach 100 green points', true, '2024-03-15', 'System', 1, true, NOW(), NOW()),
(2, 'POINTS_MILESTONE', 'Green Enthusiast', 'Earned 200 green points', 'https://example.com/green-enthusiast.png', 200, '#2E7D32', 'Reach 200 green points', true, '2024-05-20', 'System', 2, true, NOW(), NOW()),
(3, 'YEAR_MILESTONE', 'First Year Caretaker', 'Cared for plant for one year', 'https://example.com/first-year.png', 50, '#FF9800', 'Complete one year of plant care', true, '2024-01-15', 'System', 1, true, NOW(), NOW());

-- Update school statistics
UPDATE schools SET total_students = 3, total_plants = 3 WHERE id = 1;

-- Create user accounts for students
INSERT IGNORE INTO users (id, username, email, password, first_name, last_name, is_active, is_email_verified, student_id, created_at, updated_at) 
VALUES 
(2, 'rahul.sharma', 'rahul.sharma@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Rahul', 'Sharma', true, true, 1, NOW(), NOW()),
(3, 'priya.patel', 'priya.patel@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Priya', 'Patel', true, true, 2, NOW(), NOW()),
(4, 'arjun.singh', 'arjun.singh@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Arjun', 'Singh', true, true, 3, NOW(), NOW());

-- Assign roles to students
INSERT IGNORE INTO user_roles (user_id, role) VALUES 
(2, 'STUDENT'),
(3, 'STUDENT'),
(4, 'STUDENT');

-- Create user account for school admin
INSERT IGNORE INTO users (id, username, email, password, first_name, last_name, is_active, is_email_verified, school_admin_id, created_at, updated_at) 
VALUES (5, 'school.admin', 'admin@greenvalleyschool.edu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'School', 'Admin', true, true, 1, NOW(), NOW());

-- Assign role to school admin
INSERT IGNORE INTO user_roles (user_id, role) VALUES (5, 'SCHOOL_ADMIN');

COMMIT;
