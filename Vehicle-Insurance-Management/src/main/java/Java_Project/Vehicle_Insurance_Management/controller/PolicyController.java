package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/user/policy")
    public String showPolicyPage(Model model) {
        // Add policy data here if needed
        return "user-policy-page"; // This should match the HTML file name (user-policy-page.html)
    }
    @GetMapping("/user/policy/{id}/details")
    public String showPolicyDetails(@PathVariable("id") Long policyId, Model model, Principal principal) {
        boolean isPurchased = policyService.isPolicyPurchased(principal.getName(), policyId);
        model.addAttribute("isPurchased", isPurchased);

        // Also include policy details if needed
        InsurancePolicy policy = policyService.getPolicyById(policyId);
        model.addAttribute("policy", policy);

        return "policy-details";
    }

    @PostMapping("/user/policy/{id}/purchase")
    public String purchasePolicy(@PathVariable("id") Long policyId, Principal principal) {
        policyService.purchasePolicy(principal.getName(), policyId);
        return "redirect:/user/policy/" + policyId + "/details?success=true";
    }


}