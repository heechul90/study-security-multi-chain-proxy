package studysecurity.multichainproxy.core.domain.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping(value = "/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "loginform";
    }

    @GetMapping(value = "/auth")
    @ResponseBody
    public Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }
}
