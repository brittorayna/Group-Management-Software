/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import uga.menik.cs4370.services.UserService;
import java.util.List;
import java.util.ArrayList;
import uga.menik.cs4370.models.Course;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;


/**
 * Handles /people URL and its sub URL paths.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    private final UserService userService;

    @Autowired
    public CourseController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Serves the /people web page.
     * 
     * Note that this accepts a URL parameter called error.
     * The value to this parameter can be shown to the user as an error message.
     * See notes in HashtagSearchController.java regarding URL parameters.
     */
    @GetMapping
public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) throws UnsupportedEncodingException, SQLException {
    ModelAndView mv = new ModelAndView("course_page");

    try {
        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);

        // Fetch the courses in which the current user is enrolled
        List<Course> enrolledCourses = userService.getEnrolledCourses(userService.getLoggedInUserId());

        mv.addObject("enrolledCourses", enrolledCourses);
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exceptions appropriately
        String errorMessage = "An error occurred while processing your request.";
        mv.addObject("errorMessage", errorMessage);
    }

    return mv;
    }
}
