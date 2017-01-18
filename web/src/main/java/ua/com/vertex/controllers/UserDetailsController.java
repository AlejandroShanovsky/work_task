package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;

@Controller
@RequestMapping(value = "/userDetailsByID")
@SessionAttributes("users")
public class UserDetailsController {

    UserLogic userLogic;

    @Autowired
    public UserDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView getUserDetailsByID(@RequestParam("userID") int userID) {
        int userIDq = userID;
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;

    }
}

