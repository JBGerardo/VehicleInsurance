package Java_Project.Vehicle_Insurance_Management.controller;


import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Java_Project.Vehicle_Insurance_Management.repository.ClaimRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClaimController {
    private final PolicyService policyService;

    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;

    @Autowired
    public ClaimController(PolicyService policyService,
                           UserRepository userRepository,
                           ClaimRepository claimRepository) {
        this.policyService = policyService;
        this.userRepository = userRepository;
        this.claimRepository = claimRepository;
    }

    @GetMapping("/user/claim")
    public String showClaimForm(Model model, Principal principal) {
        String username = principal.getName();
        List<InsurancePolicy> policies = policyService.getPurchasedPolicies(username);

        // Define claim types based on policy IDs or names
        Map<Long, List<String>> policyClaimTypes = new HashMap<>();

        for (InsurancePolicy policy : policies) {
            if (policy.getName().toLowerCase().contains("comprehensive")) {
                policyClaimTypes.put(policy.getId(), List.of("Accident", "Theft", "Fire", "Natural Disaster", "Vandalism", "Animal Collision", "Glass Damage"));
            } else if (policy.getName().toLowerCase().contains("third-party")) {
                policyClaimTypes.put(policy.getId(), List.of("Accident", "Third-Party Damage"));
            } else if (policy.getName().toLowerCase().contains("collision")) {
                policyClaimTypes.put(policy.getId(), List.of("Accident", "Collision Impact", "Glass Damage"));
            } else {
                policyClaimTypes.put(policy.getId(), List.of("General Claim")); // fallback
            }
        }

        List<String> vendors = List.of("AutoFix Center", "DriveWell Repairs", "Speedy Garage", "ProMech Auto");

        model.addAttribute("policies", policies);
        model.addAttribute("policyClaimTypes", policyClaimTypes); // ✅ New map
        model.addAttribute("vendors", vendors);

        return "Member/Claim/claim-form";
    }

    @PostMapping("/user/claim/submit")
    public String submitClaim(@RequestParam Long policyId,
                              @RequestParam String vehicleDetails,
                              @RequestParam String claimType,
                              @RequestParam String vendor,
                              @RequestParam String description,
                              Principal principal) {

        User user = userRepository.findByUsername(principal.getName());
        InsurancePolicy policy = policyService.getPolicyById(policyId);

        Claim claim = new Claim();
        claim.setUser(user);
        claim.setMember(user.getMember()); // ✅ link member here
        claim.setPolicy(policy);
        claim.setVehicleDetails(vehicleDetails);
        claim.setType(claimType);
        claim.setVendorStatus("Pending"); // ✅ Initial vendor status
        claim.setVendor(vendor);
        claim.setDescription(description);
        claim.setStatus("Pending");
        claim.setClaimDate(LocalDate.now());

        claimRepository.save(claim);

        return "redirect:/user/claim/confirmation";

    }

    @GetMapping("/user/claim/confirmation")
    public String showClaimConfirmation() {
        return "Member/Claim/claim-confirmation";
    }

    @GetMapping("/user/claim/history")
    public String showClaimHistory(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        List<Claim> claims = claimRepository.findByUserId(user.getId());

        model.addAttribute("claims", claims);
        return "Member/Claim/claim-history";
    }



}
