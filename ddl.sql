-- Create the database.
create database if not exists cs4370_final_project;

-- Use the created database.
use cs4370_final_project;

-- User Table
CREATE TABLE IF NOT EXISTS User (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username CHAR(255) UNIQUE,
    password CHAR(255),
    f_name CHAR(255),
    l_name CHAR(255),
    userType CHAR(10) NOT NULL  -- 'Student' or 'Professor'
);

-- Student Table (inherits from User)
CREATE TABLE IF NOT EXISTS Student (
    studentID INT,
    strengths CHAR(200),
    weaknesses CHAR(200),
    PRIMARY KEY (studentID),
    FOREIGN KEY (studentID) REFERENCES User(userID) ON DELETE CASCADE
);

-- Professor Table (inherits from User)
CREATE TABLE IF NOT EXISTS Professor (
    profID INT,
    PRIMARY KEY (profID),
    FOREIGN KEY (profID) REFERENCES User(userID) ON DELETE CASCADE
);

-- Course Table
CREATE TABLE IF NOT EXISTS Course (
    courseID INT PRIMARY KEY,
    course_name CHAR(55),
    profID INT,
    num_groups INT,
    students_per_group INT,
    FOREIGN KEY (profID) REFERENCES Professor(profID) ON DELETE CASCADE
);

-- ProjectGroup Table
CREATE TABLE IF NOT EXISTS ProjectGroup (
    groupID INT AUTO_INCREMENT PRIMARY KEY,
    courseID INT,
    groupName CHAR(55),
    current_size INT DEFAULT 0,
    max_size INT,
    FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
);

-- Group_membership Table
CREATE TABLE IF NOT EXISTS Group_membership (
    studentID INT,
    groupID INT,
    PRIMARY KEY (studentID, groupID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE,
    FOREIGN KEY (groupID) REFERENCES ProjectGroup(groupID) ON DELETE CASCADE
);

-- Enrolled_in Table
CREATE TABLE IF NOT EXISTS Enrolled_in (
    studentID INT,
    courseID INT,
    PRIMARY KEY (studentID, courseID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE,
    FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
);

-- Teaches Table
CREATE TABLE IF NOT EXISTS Teaches (
    profID INT,
    courseID INT,
    PRIMARY KEY (profID, courseID),
    FOREIGN KEY (profID) REFERENCES Professor(profID) ON DELETE CASCADE,
    FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
); 
