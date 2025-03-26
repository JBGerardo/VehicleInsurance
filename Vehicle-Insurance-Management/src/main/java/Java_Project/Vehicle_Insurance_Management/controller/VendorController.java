package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Dashboard / Profile Page
    @GetMapping("/vendor/dashboard")
    public String viewVendorProfile(Model model) {
        // For now, fetching a sample vendor. In production, get vendor from logged-in user
        Vendor vendor = vendorService.getLoggedInVendor(); // Assume this method exists
        model.addAttribute("vendor", vendor);
        return "Vendor/vendor-profile"; // points to src/main/resources/templates/Vendor/vendor_profile.html
    }

    // Edit Profile (optional placeholder if you plan to add editing functionality)
    @GetMapping("/vendor/profile/edit")
    public String editVendorProfile(Model model) {
        Vendor vendor = vendorService.getLoggedInVendor();
        model.addAttribute("vendor", vendor);
        return "Vendor/edit_vendor_profile"; // create this view later if needed
    }
}