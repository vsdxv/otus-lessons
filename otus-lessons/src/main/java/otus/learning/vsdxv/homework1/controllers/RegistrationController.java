package otus.learning.vsdxv.homework1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import otus.learning.vsdxv.homework1.model.User;
import otus.learning.vsdxv.homework1.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUsername());
//        User userExists = user;
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "Пользователь с таким именем уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Пользователь зарегистрирован");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }
}
