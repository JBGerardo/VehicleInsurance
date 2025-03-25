package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user != null && user.isAdmin()) {
            // Add the admin user details to the model
            model.addAttribute("admin", user.getMember());
            return "Administrator/admin-profile"; // Return to the admin profile page
        }

        // Redirect to an error page if the user is not an admin
        return "redirect:/error";  // You can customize this to redirect to an appropriate page
    }
}


