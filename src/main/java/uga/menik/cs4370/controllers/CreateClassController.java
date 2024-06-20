package uga.menik.cs4370.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uga.menik.cs4370.models.Course;
import uga.menik.cs4370.services.CourseService;
import uga.menik.cs4370.services.UserService;

import java.sql.SQLException;
import java.io.UnsupportedEncodingException;
import uga.menik.cs4370.models.User;

@Controller
@RequestMapping("/professor/create-a-class")
public class CreateClassController {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public CreateClassController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("create_class_page");

        // If an error occurred, you can set the following property with the
        // error message to show the error message to the user.
        mv.addObject("errorMessage", error);

        return mv;
    }

    @PostMapping("/newclass")
    public String createCourse(@RequestParam(name = "courseId") int courseId,
                               @RequestParam(name = "courseName") String courseName,
                               @RequestParam(name = "numGroups") int numGroups,
                               @RequestParam(name = "groupSize") int groupSize) throws UnsupportedEncodingException, SQLException {
        try {
            User professor = userService.getLoggedInUser();
            Course createdCourse = courseService.addCourse(courseId, courseName, professor, numGroups, groupSize);
            if (createdCourse != null) {
                System.out.println("Course created successfully: " + createdCourse);
                // Course creation was successful
                return "redirect:/professor";
            } else {
                // Course creation failed
                System.out.println("Failed to create the course");
                return "redirect:/professor/create-a-class?error=" + URLEncoder.encode("Failed to create the course. Please try again.", StandardCharsets.UTF_8);
            }
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            String message = URLEncoder.encode("An error occurred while processing your request.", "UTF-8");
            return "redirect:/class-code?error=" + message;
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            String message = URLEncoder.encode("An unexpected error occurred while processing your request.", "UTF-8");
            return "redirect:/class-code?error=" + message;
        }
    }
}
