final String updateSWSql = "UPDATE Student SET strengths = ?, weaknesses = ? WHERE studentID = ?";
-- Used to update the strenths and weakenesses of a student
-- http://localhost:8080/SW

String sql = "INSERT INTO Course (courseId, course_name, profID, num_groups, students_per_group) VALUES (?, ?, ?, ?, ?)";
-- Used to insert a new row into the course table
-- http://localhost:8080/professor/create-a-class

final String sql = "select * from User where username = ?";
-- Used in the authentication process of the user
-- http://localhost:8080/login

final String registerSql = "insert into User (username, password, f_name, l_name, userType) values (?, ?, ?, ?, ?)";
-- Used to reiseter a user, adding the row into the database
-- http://localhost:8080/register

final String registerSql = "insert into Student (studentID) values (?)";
-- Used to add the newly registered user to the Student Table.
-- http://localhost:8080/register

final String registerSql = "insert into Professor (profID) values (?)";
-- Used to add the newly registered user to the Professor table
-- http://localhost:8080/register

final String sql = "SELECT COUNT(*) FROM Course WHERE courseID = ?";
-- Used to check if a Course with a certain code exists in the Course table
-- http://localhost:8080/course

final String sql = "SELECT COUNT(*) FROM Enrolled_in WHERE studentId = ? AND courseID = ?";
-- Used to check if a student is enrolled in a certain course
-- http://localhost:8080/

final String sql = "SELECT courseID FROM Course WHERE courseID = ?";
-- Used to select a course by id
-- http://localhost:8080/class-code

final String sql = "INSERT INTO Enrolled_in (studentId, courseID) VALUES (?, ?)";
-- used to enroll a student in a course by inserting them in to the table.
-- http://localhost:8080/class-code/

final String sql = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM Enrolled_in WHERE studentId = ?)";
-- used to select courses in which a student is enrolled
-- http://localhost:8080/