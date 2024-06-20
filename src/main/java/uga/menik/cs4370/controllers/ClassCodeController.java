/**
 * Copyright (c) 2024 Sami Menik, PhD. All rights reserved.
 * This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project.
 * Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers.
 * Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
 */
package uga.menik.cs4370.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import uga.menik.cs4370.services.UserService;
import java.sql.SQLException;

@Controller
@RequestMapping("/class-code")
public class ClassCodeController {

    private final UserService userService;

    public ClassCodeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("class_code_page");

        // If an error occurred, you can set the following property with the
        // error message to show the error message to the user.
        mv.addObject("errorMessage", error);

        return mv;
    }

    @PostMapping("/join")
    public String joinClass(@RequestParam("classCode") String classCode) throws UnsupportedEncodingException, SQLException {
        int loggedInUserId = userService.getLoggedInUserId();
        int courseId = Integer.parseInt(classCode);

        if (loggedInUserId != 0) {
            try {
                boolean courseExists = userService.doesCourseExist(courseId);
                boolean isEnrolled = userService.isStudentEnrolled(loggedInUserId, courseId);

                if (courseExists) {
                    if (isEnrolled) {
                        // Handle case where student is already enrolled
                        String message = URLEncoder.encode("You are already enrolled in this course.", "UTF-8");
                        return "redirect:/class-code?error=" + message;
                    } else {
                        userService.enrollStudentInCourse(loggedInUserId, courseId);
                        return "redirect:/course";
                    }
                } else {
                    // Handle case where course does not exist
                    String message = URLEncoder.encode("Course does not exist.", "UTF-8");
                    return "redirect:/class-code?error=" + message;
                }
            } catch (SQLException e) {
                // Handle SQL exception
                e.printStackTrace();
                String message = URLEncoder.encode("An error occurred while processing your request.", "UTF-8");
                return "redirect:/class-code?error=" + message;
            }
        } else {
            // Handle case where no user is logged in
            String message = URLEncoder.encode("You must be logged in to join a class.", "UTF-8");
            return "redirect:/class-code?error=" + message;
        }
    }
}

