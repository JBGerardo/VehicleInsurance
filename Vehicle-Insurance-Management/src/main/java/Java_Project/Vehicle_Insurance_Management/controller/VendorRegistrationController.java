package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        vendor.setStatus("Pending"); // New vendors default to 'Pending'
        Vendor savedVendor = vendorRepository.save(vendor);

        user.setVendor(savedVendor); // Optional: if User has a Vendor relationship
        user.setAdmin(false);        // Vendors are not admins
        userRepository.save(user);

        model.addAttribute("message", "Vendor application submitted successfully!");
        return "redirect:/login";
    }
}
