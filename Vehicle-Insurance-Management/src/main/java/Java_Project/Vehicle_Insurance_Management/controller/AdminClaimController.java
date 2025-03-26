package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.repository.ClaimRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/manage-claims")
public class AdminClaimController {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private UserRepository userRepository;


    // View all claims (with optional search)
    @GetMapping
    public String viewClaims(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<Claim> filteredClaims;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            filteredClaims = claimRepository.findByTypeContainingIgnoreCase(searchQuery);
        } else {
            filteredClaims = claimRepository.findAll();
        }

        model.addAttribute("filteredClaims", filteredClaims);
        model.addAttribute("searchQuery", searchQuery);

        return "Administrator/ManageClaim/manage-claim-table";
    }

    // Show review/edit form
    @GetMapping("/review/{id}")
    public String reviewClaimForm(@PathVariable Long id, Model model) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            model.addAttribute("claim", optionalClaim.get());
            return "Administrator/ManageClaim/manage-review-claim"; // ✅ updated HTML name
        } else {
            return "redirect:/admin/manage-claims";
        }
    }

    @PostMapping("/review/{id}")
    public String updateClaim(@PathVariable Long id,
                              @ModelAttribute("claim") Claim updatedClaim,
                              Principal principal) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            Claim claim = optionalClaim.get();

            // ✅ Update only what's allowed
            claim.setStatus(updatedClaim.getStatus());
            claim.setVendorStatus(updatedClaim.getVendorStatus());

            // ✅ Set the approver using the logged-in admin's username
            User approver = userRepository.findByUsername(principal.getName());
            claim.setApprover(approver);

            claimRepository.save(claim);
        }

        return "redirect:/admin/manage-claims";
    }


    // Delete claim
    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable Long id) {
        if (claimRepository.existsById(id)) {
            claimRepository.deleteById(id);
        }
        return "redirect:/admin/manage-claims";
    }
}
