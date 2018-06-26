package core.Controller;

import core.Model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("user")
public class Controller {
    @PostMapping("signup")
    public User  signup(User user){
        return user;
    }

    @PreAuthorize("USER")
    @PostMapping("getuserinfo")
    public String getUser(){
        return "bbbbb";
    }


}
