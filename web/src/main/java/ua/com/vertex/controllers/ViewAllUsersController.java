package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.ViewAllUsersLogic;

@Controller
@RequestMapping(value = "/viewAllUsers")
@SessionAttributes("users")
public class ViewAllUsersController {


    ViewAllUsersLogic viewAllUsersLogic;

    @Autowired
    public ViewAllUsersController(ViewAllUsersLogic viewAllUsersLogic) {
        this.viewAllUsersLogic = viewAllUsersLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView viewAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("viewAllUsers");
        modelAndView.addObject("users", viewAllUsersLogic.getListUsers());
        viewAllUsersLogic.getListUsers();
        return modelAndView;

    }
}

