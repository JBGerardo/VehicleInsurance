package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.model.StripeSession;
import Java_Project.Vehicle_Insurance_Management.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class PolicyController {


    private PolicyService policyService;
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/user/policy")
    public String showPolicyPage(Model model) {
        // Add policy data here if needed
        return "user-policy-page"; // This should match the HTML file name (user-policy-page.html)
    }
//    @GetMapping("/user/policy/{id}/details")
//    public String showPolicyDetails(@PathVariable("id") Long policyId, Model model, Principal principal) {
//        boolean isPurchased = policyService.isPolicyPurchased(principal.getName(), policyId);
//        model.addAttribute("isPurchased", isPurchased);
//
//        // Also include policy details if needed
//        InsurancePolicy policy = policyService.getPolicyById(policyId);
//        model.addAttribute("policy", policy);
//
//        return "policy-details";
//    }

    @GetMapping("/user/policy/{id}/details")
    public String showPolicyDetails(@PathVariable("id") Long policyId, Model model, Principal principal) {
        InsurancePolicy policy = policyService.getPolicyById(policyId);
        boolean isPurchased = policyService.isPolicyPurchased(principal.getName(), policyId);

        model.addAttribute("policy", policy);
        model.addAttribute("isPurchased", isPurchased);

        // Dynamically route based on policy ID
        return switch (policyId.intValue()) {
            case 1 -> "policy-details";
            case 2 -> "policy-2-details";
            case 3 -> "policy-3-details";
            default -> "redirect:/user/policy"; // fallback if invalid ID
        };
    }

    @GetMapping("/user/policy2")
    public String showPolicy2Details(Model model, Principal principal) {
        InsurancePolicy policy = policyService.getPolicyById(2L);
        boolean isPurchased = policyService.isPolicyPurchased(principal.getName(), 2L);

        model.addAttribute("policy", policy);
        model.addAttribute("isPurchased", isPurchased);
        return "policy-2-details";
    }

    @GetMapping("/user/policy3")
    public String showPolicy3Details(Model model, Principal principal) {
        InsurancePolicy policy = policyService.getPolicyById(3L);
        boolean isPurchased = policyService.isPolicyPurchased(principal.getName(), 3L);

        model.addAttribute("policy", policy);
        model.addAttribute("isPurchased", isPurchased);
        return "policy-3-details";
    }

    @PostMapping("/user/policy/{id}/purchase")
    public String purchasePolicy(@PathVariable("id") Long policyId, Principal principal) {
        try {
            StripeSession session = policyService.createStripeSession(principal.getName(), policyId);
            return "redirect:" + session.getUrl(); // ✅ Redirect to Stripe
        } catch (StripeException e) {
            e.printStackTrace();
            return "redirect:/user/policy/" + policyId + "/details?error=stripe";
        }
    }

    @GetMapping("/user/policy/{id}/pay")
    public String redirectToStripe(@PathVariable Long id, Principal principal, Model model) throws StripeException {
        String username = principal.getName();
        StripeSession session = policyService.createStripeSession(username, id); // returns sessionId & amount
        model.addAttribute("stripePublicKey", "your-publishable-key");
        model.addAttribute("sessionId", session.getSessionId());
        return "payment";
    }
    @GetMapping("/user/payment/success")
    public String handlePaymentSuccess(Principal principal, @RequestParam(required = false) Long policyId) {
        if (principal != null && policyId != null) {
            policyService.purchasePolicy(principal.getName(), policyId);
        }
        return "payment-success.html";
    }
    @GetMapping("/user/purchased-policies")
    public String showPurchasedPolicies(Model model, Principal principal) {
        String username = principal.getName();
        List<InsurancePolicy> purchasedPolicies = policyService.getPurchasedPolicies(username);

        model.addAttribute("policies", purchasedPolicies);
        return "user-purchasedpolicy";
    }



}