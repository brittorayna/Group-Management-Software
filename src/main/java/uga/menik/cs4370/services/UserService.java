/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import uga.menik.cs4370.models.User;
import uga.menik.cs4370.models.Course;

/**
 * This is a service class that enables user related functions.
 * The class interacts with the database through a dataSource instance.
 * See authenticate and registerUser functions for examples.
 * This service object is spcial. It's lifetime is limited to a user session.
 * Usual services generally have application lifetime.
 */
@Service
@SessionScope
public class UserService {

    // dataSource enables talking to the database.
    private final DataSource dataSource;
    // passwordEncoder is used for password security.
    private final BCryptPasswordEncoder passwordEncoder;
    // This holds 
    private User loggedInUser = null;

    /**
     * See AuthInterceptor notes regarding dependency injection and
     * inversion of control.
     */
    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Authenticate user given the username and the password and
     * stores user object for the logged in user in session scope.
     * Returns true if authentication is succesful. False otherwise.
     */
    public boolean authenticate(String username, String password) throws SQLException {
        // Note the ? mark in the query. It is a place holder that we will later replace.
        final String sql = "select * from User where username = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Following line replaces the first place holder with username.
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Traverse the result rows one at a time.
                // Note: This specific while loop will only run at most once 
                // since username is unique.
                while (rs.next()) {
                    // Note: rs.get.. functions access attributes of the current row.
                    String storedPasswordHash = rs.getString("password");
                    boolean isPassMatch = passwordEncoder.matches(password, storedPasswordHash);
                    // Note: 
                    if (isPassMatch) {
                        String userId = rs.getString("userId");
                        String firstName = rs.getString("f_name");
                        String lastName = rs.getString("l_Name");

                        // Initialize and retain the logged in user.
                        loggedInUser = new User(userId, firstName, lastName);
                    }
                    return isPassMatch;
                }
            }
        }
        return false;
    }

    /**
     * Logs out the user.
     */
    public void unAuthenticate() {
        loggedInUser = null;
    }

    /**
     * Checks if a user is currently authenticated.
     */
    public boolean isAuthenticated() {
        return loggedInUser != null;
    }

    /**
     * Retrieves the currently logged-in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Registers a new user with the given details.
     * Returns true if registration is successful. If the username already exists,
     * a SQLException is thrown due to the unique constraint violation, which should
     * be handled by the caller.
     */
    public boolean registerUser(String username, String password, String firstName, String lastName, String userType)
            throws SQLException {
        // Note the ? marks in the SQL statement. They are placeholders like mentioned above.
        final String registerSql = "insert into User (username, password, f_name, l_name, userType) values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement registerStmt = conn.prepareStatement(registerSql, Statement.RETURN_GENERATED_KEYS)) {
            // Following lines replace the placeholders 1-4 with values.
            registerStmt.setString(1, username);
            registerStmt.setString(2, passwordEncoder.encode(password));
            registerStmt.setString(3, firstName);
            registerStmt.setString(4, lastName);
            registerStmt.setString(5, userType);

            // Execute the statement and check if rows are affected.
            int rowsAffected = registerStmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = registerStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userID = generatedKeys.getInt(1);
                        if ("student".equals(userType)) {
                            return registerStudent(userID);
                        } else if ("professor".equals(userType)) {
                            return registerProfessor(userID);
                        }
                    }
                }
        }
        return false;
    }
}


     /**
     * Registers a new student with the given details.
     * Returns true if registration is successful. 
     */
    public boolean registerStudent(int userID)
            throws SQLException {
        // Note the ? marks in the SQL statement. They are placeholders like mentioned above.
        final String registerSql = "insert into Student (studentID) values (?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement registerStmt = conn.prepareStatement(registerSql)) {
            // Following lines replace the placeholders 1-4 with values.
            registerStmt.setInt(1, userID);

            // Execute the statement and check if rows are affected.
            int rowsAffected = registerStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


     /**
     * Registers a new student with the given details.
     * Returns true if registration is successful. 
     */
    public boolean registerProfessor(int userID)
            throws SQLException {
        // Note the ? marks in the SQL statement. They are placeholders like mentioned above.
        final String registerSql = "insert into Professor (profID) values (?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement registerStmt = conn.prepareStatement(registerSql)) {
            // Following lines replace the placeholders 1-4 with values.
            registerStmt.setInt(1, userID);

            // Execute the statement and check if rows are affected.
            int rowsAffected = registerStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

//for the course stuff
/**
 * Checks if a course with the given course code exists in the Course table.
 */
public boolean doesCourseExist(int courseID) throws SQLException {
    final String sql = "SELECT COUNT(*) FROM Course WHERE courseID = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, courseID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}

/**
 * Checks if the logged-in student is already enrolled in the course with the given course code.
 */
public boolean isStudentEnrolled(int studentId, int courseID) throws SQLException {
    final String sql = "SELECT COUNT(*) FROM Enrolled_in WHERE studentId = ? AND courseID = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, studentId);
        pstmt.setInt(2, courseID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}

/**
 * Retrieves the ID of the currently logged-in user.
 * Returns 0 if no user is logged in.
 */
public int getLoggedInUserId() {
    if (loggedInUser != null) {
        return loggedInUser.getUserId(); // This should return an int
    } else {
        return 0;
    }
}

public int getCourseId(int courseID) throws SQLException {
    final String sql = "SELECT courseID FROM Course WHERE courseID = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, courseID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("courseID"); // This should return an int
            }
        }
    }
    return 0;
}

/**
* Enrolls a student in a course by inserting a record into the Enrolled_in table.
* Returns true if the enrollment is successful, false otherwise.
*/
public boolean enrollStudentInCourse(int studentId, int courseID) throws SQLException {
    final String sql = "INSERT INTO Enrolled_in (studentId, courseID) VALUES (?, ?)";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, studentId);
        pstmt.setInt(2, courseID);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }
}

/**
     * Retrieves the list of courses in which the user is enrolled.
     */
    public ArrayList<Course> getEnrolledCourses(int userId) throws SQLException {
        ArrayList<Course> enrolledCourses = new ArrayList<Course>();
    
        // Query the database to fetch enrolled courses for the given userId
        final String sql = "SELECT * FROM Course WHERE courseID IN (SELECT courseID FROM Enrolled_in WHERE studentId = ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create a new Course object for each retrieved row
                    Course course = new Course(
                        rs.getInt("courseID"),
                        rs.getString("course_name"),
                        rs.getInt("profID"),
                        rs.getInt("num_groups"),
                        rs.getInt("students_per_group")
                    );
                    // Add the course to the list
                    enrolledCourses.add(course);
                }
            }
        }
        return enrolledCourses;
    }
    
}
