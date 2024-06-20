package uga.menik.cs4370.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/professor/view-a-class")
public class ViewClassController {

    @GetMapping
    public ModelAndView professorPage() {
        ModelAndView mv = new ModelAndView("prof_class");
        // fix logic to retireve professor data from db so we can redirect here from a professor logging in
        return mv;
    }

    //other things can be added
    
}
