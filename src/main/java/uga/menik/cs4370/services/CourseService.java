
/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uga.menik.cs4370.models.Course;
import uga.menik.cs4370.models.User;

import java.sql.Statement;

/**
 * This service contains people related functions.
 */
@Service
public class CourseService {
    // dataSource enables talking to the database.
    private final DataSource dataSource;

    /**
     * See AuthInterceptor notes regarding dependency injection and
     * inversion of control.
     */
    @Autowired
    public CourseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Creates a new class for the current session professor with the given course information from the form.
     * 
     * @param courseId     Unique identifier for the course (CRN).
     * @param course_name    Name of the course.
     * @param profID   Professor ID that teaches the course.
     * @param num_groups       Number of groups needed for the course.
     * @param students_per_group       Number of students needed per group.
     * @return true or false depending on successful course creation.
     * If the course creation in the database is successful, then returns true, else returns false.
     */ 
    public Course addCourse(int courseId, String course_name, User professor, int num_groups, int students_per_group) throws SQLException {
        Course newCourse = null;
        
        String sql = "INSERT INTO Course (courseId, course_name, profID, num_groups, students_per_group) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
            pstmt.setInt(1, courseId);
            pstmt.setString(2, course_name);
            pstmt.setInt(3, professor.getUserId());
            pstmt.setInt(4, num_groups);
            pstmt.setInt(5, students_per_group);
        
            int rowsAffected = pstmt.executeUpdate();
        
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedCourseId = generatedKeys.getInt(1);
                        newCourse = new Course(generatedCourseId, course_name, professor.getUserId(), num_groups, students_per_group);
                    }
                }
            }
        } 
        // Don't catch SQLException here, let it propagate to the calling method
        return newCourse;
    }
    
    
}
