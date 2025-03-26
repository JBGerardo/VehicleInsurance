package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/manage-vendors")
public class AdminVendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Display list of vendors
    @GetMapping
    public String viewVendors(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<Vendor> filteredVendors;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            filteredVendors = vendorRepository.findByNameContainingIgnoreCase(searchQuery);
        } else {
            filteredVendors = vendorRepository.findAll();
        }

        model.addAttribute("filteredVendors", filteredVendors);
        model.addAttribute("searchQuery", searchQuery);

        return "Administrator/ManageVendor/manage-vendor-table";
    }

    // ✅ Show edit form
    @GetMapping("/edit/{id}")
    public String editVendorForm(@PathVariable Long id, Model model) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(id);
        if (vendorOpt.isPresent()) {
            model.addAttribute("vendor", vendorOpt.get());
            return "Administrator/ManageVendor/manage-vendor-edit";
        }
        return "redirect:/admin/manage-vendors";
    }

    // ✅ Save updated vendor
    @PostMapping("/edit/{id}")
    public String updateVendor(@PathVariable Long id, @ModelAttribute("vendor") Vendor updatedVendor) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(id);
        if (vendorOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            vendor.setName(updatedVendor.getName());
            vendor.setStatus(updatedVendor.getStatus());

            vendorRepository.save(vendor);
        }
        return "redirect:/admin/manage-vendors";
    }

    // ✅ Delete vendor
    @GetMapping("/delete/{id}")
    public String deleteVendor(@PathVariable Long id) {
        // Remove vendor reference from users
        List<User> users = userRepository.findByVendorId(id);
        for (User user : users) {
            user.setVendor(null);
            userRepository.save(user);
        }

        // Delete the vendor
        vendorRepository.deleteById(id);
        return "redirect:/admin/manage-vendors";
    }


}