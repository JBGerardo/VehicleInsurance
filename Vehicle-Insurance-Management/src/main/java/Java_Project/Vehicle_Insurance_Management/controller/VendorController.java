package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.ClaimRepository;
import Java_Project.Vehicle_Insurance_Management.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ClaimRepository claimRepository;

    // Dashboard / Profile Page
    @GetMapping("/vendor/dashboard")
    public String viewVendorProfile(Model model) {
        // For now, fetching a sample vendor. In production, get vendor from logged-in user
        Vendor vendor = vendorService.getLoggedInVendor(); // Assume this method exists
        model.addAttribute("vendor", vendor);
        return "Vendor/vendor-profile"; // points to src/main/resources/templates/Vendor/vendor_profile.html
    }


    @GetMapping("/vendor/view-claims")
    public String viewVendorClaims(Model model) {
        Vendor vendor = vendorService.getLoggedInVendor();

        if (vendor == null) {
            return "redirect:/vendor/dashboard";
        }

        List<Claim> vendorClaims = claimRepository.findByVendor(vendor);

        model.addAttribute("vendorClaims", vendorClaims); // ✅ This must match HTML

        return "Vendor/Claims/vendor-claim";
    }

    @GetMapping("/vendor/claims/update/{id}")
    public String showClaimReview(@PathVariable Long id, Model model) {
        Claim claim = claimRepository.findById(id).orElse(null);
        if (claim == null) {
            return "redirect:/vendor/view-claims";
        }
        model.addAttribute("claim", claim);
        return "Vendor/Claims/vendor-claim-review"; // Make sure this file exists
    }

    @PostMapping("/vendor/claims/update/{id}")
    public String updateClaim(@PathVariable Long id,
                              @RequestParam String vendorStatus,
                              @RequestParam(required = false) String vendorNotes) {
        Claim claim = claimRepository.findById(id).orElse(null);

        if (claim != null) {
            claim.setVendorStatus(vendorStatus);
            // Optional: Save vendorNotes if you have this field in Claim entity
            claimRepository.save(claim);
        }

        return "redirect:/vendor/view-claims";
    }




}