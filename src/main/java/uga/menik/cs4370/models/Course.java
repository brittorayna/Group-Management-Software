/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.models;

/**
 * Represents the basic structure of a post in the micro blogging platform.
 * This class serves as a base for both posts and comments.
 */
public class Course {
    
    /**
     * Unique identifier for the course (CRN).
     */
    private final int courseId;

    /**
     * Name of the course.
     */
    private final String course_name;

    /**
     * Professor ID that teaches the course.
     */
    private final int profID;

    /**
     * Number of groups needed for the course.
     */
    private final int num_groups;
    /**
     * Number of students needed per group.
     */
    private final int students_per_group;

    /**
     * Constructs a BasicPost with specified details.
     *
     * @param courseId     Unique identifier for the course.
     * @param course_name    Name of the course.
     * @param profID   Professor ID that teaches the course.
     * @param num_groups       Number of groups needed for the course.
     * @param students_per_group       Number of students needed per group.
     */
    public Course(int courseId, String course_name, int profID, int num_groups, int students_per_group) {
        this.courseId = courseId;
        this.course_name = course_name;
        this.profID = profID;
        this.num_groups = num_groups;
        this.students_per_group = students_per_group;
    }

    /**
     * Returns the course ID.
     *
     * @return the course ID
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Returns the name of course.
     *
     * @return the name of course.
     */
    public String getCourse_name() {
        return course_name;
    }

    /**
     * Returns the professor ID.
     *
     * @return the professor ID.
     */
    public int getProfID() {
        return profID;
    }

    /**
     * Returns the number of groups in a course.
     *
     * @return the number of groups in a course.
     */
    public int getNum_groups() {
        return num_groups;
    }


    /**
     * Returns the number of studnets in each group.
     *
     * @return the number of studnets in each group.
     */
    public int getStudents_per_group() {
        return students_per_group;
    }
}
