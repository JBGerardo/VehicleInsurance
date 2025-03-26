package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserRole;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VendorRegistrationController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Show vendor sign-up form
    @GetMapping("/partner-register")
    public String showVendorRegistrationForm(Model model) {
        model.addAttribute("vendor", new Vendor());
        model.addAttribute("user", new User());
        return "vendor-register"; // Template: vendor-register.html
    }

    // ✅ Handle vendor registration
    @PostMapping("/partner-register")
    public String registerVendor(@ModelAttribute Vendor vendor, @ModelAttribute User user, Model model) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists.");
            return "vendor-register";
        }

        // Save the vendor (with default status "Pending")
        vendor.setStatus("Pending");
        Vendor savedVendor = vendorRepository.save(vendor);

        // Prepare the User account
        user.setVendor(savedVendor);             // Link vendor profile
        user.setAdmin(false);                    // Not an admin
        user.setRole(UserRole.ROLE_VENDOR);      // ✅ Set role
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Encrypt password

        userRepository.save(user);

        model.addAttribute("message", "Vendor application submitted successfully!");
        return "redirect:/login";
    }
}
