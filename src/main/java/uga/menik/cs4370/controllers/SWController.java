/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import uga.menik.cs4370.services.UserService;
import uga.menik.cs4370.models.User;

import org.springframework.web.bind.annotation.PostMapping;


/**
 * Handles /people URL and its sub URL paths.
 */
@Controller
@RequestMapping("/SW")
public class SWController {

    private final DataSource dataSource;
    private final UserService userService;


    @Autowired
    public SWController(DataSource dataSource, UserService userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }
    // Inject UserService and PeopleService instances.
    // See LoginController.java to see how to do this.
    // Hint: Add a constructor with @Autowired annotation.


    /**
     * Serves the /people web page.
     * 
     * Note that this accepts a URL parameter called error.
     * The value to this parameter can be shown to the user as an error message.
     * See notes in HashtagSearchController.java regarding URL parameters.
     *
     * @param error An optional error message to display to the user (query parameter).
     * @return ModelAndView object representing the people_page template with followable users and error message if present.
     */
    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        // See notes on ModelAndView in BookmarksController.java.
        ModelAndView mv = new ModelAndView("strengths_weaknesses_page");

        // Following line populates sample data.
        // You should replace it with actual data from the database.
        // Use the PeopleService instance to find followable users.
        // Use UserService to access logged in userId to exclude.
  
        // If an error occured, you can set the following property with the
        // error message to show the error message to the user.
        // An error message can be optionally specified with a url query parameter too.
   
        // Enable the following line if you want to show no content message.
        // Do that if your content list is empty.
        // mv.addObject("isNoContent", true);
        
        return mv;
    }


        /**
     * This handles user registration form submissions.
     * See notes from LoginController.java.
     */
    @PostMapping
    public String updateSW(@RequestParam("strengths") String strenths,
            @RequestParam("weaknesses") String weaknesses) throws UnsupportedEncodingException {
        // Passwords should have at least 3 chars.

            final String updateSWSql = "UPDATE Student SET strengths = ?, weaknesses = ? WHERE studentID = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateSWSql)) {
                
                User currentUser = userService.getLoggedInUser();
                int studentID = currentUser.getUserId();

                // Replace placeholders with new values
                pstmt.setString(1, "New Strengths");
                pstmt.setString(2, "New Weaknesses");
                pstmt.setInt(3, studentID);  // Set the studentID that should be updated
            
                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();
            

                if (rowsAffected > 0) {
                    String message = URLEncoder
                            .encode("Strengths/Weaknesses update failed. Please try again.", "UTF-8");
                    return "redirect:/profile";
                } else {
                    // If the registration fails redirect to registration page with a message.
                    String message = URLEncoder
                            .encode("Strengths/Weaknesses update failed. Please try again.", "UTF-8");
                    return "redirect:/register?error=" + message;
                }
        } catch (Exception e) {
            // If the registration fails redirect to registration page with a message.
            String message = URLEncoder
                    .encode("An error occurred: " + e.getMessage(), "UTF-8");
            return "redirect:/register?error=" + message;
        }
    }

}
