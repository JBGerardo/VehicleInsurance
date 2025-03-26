package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.repository.InsurancePolicyRepository;
import org.springframework.ui.Model;  // ✅ Correct!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/manage-policies")
public class AdminPolicyController {
    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    // ✅ View all policies with optional search
    @GetMapping
    public String viewPolicies(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<InsurancePolicy> filteredPolicies;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // If you add this method in the repo, use it instead of filter
            // filteredPolicies = insurancePolicyRepository.findByNameContainingIgnoreCase(searchQuery);
            filteredPolicies = insurancePolicyRepository.findAll().stream()
                    .filter(policy -> policy.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .toList();
        } else {
            filteredPolicies = insurancePolicyRepository.findAll();
        }

        model.addAttribute("filteredPolicies", filteredPolicies);
        model.addAttribute("searchQuery", searchQuery);

        return "Administrator/ManagePolicy/manage-policy-table"; // Your policy table template
    }

    // ✅ Show the edit form
    @GetMapping("/edit/{id}")
    public String editPolicyForm(@PathVariable Long id, Model model) {
        Optional<InsurancePolicy> optionalPolicy = insurancePolicyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            model.addAttribute("policy", optionalPolicy.get());
            return "Administrator/ManagePolicy/manage-policy-edit"; // Your policy edit template
        } else {
            return "redirect:/admin/manage-policies";
        }
    }

    // ✅ Process the update form
    @PostMapping("/edit/{id}")
    public String updatePolicy(@PathVariable Long id, @ModelAttribute("policy") InsurancePolicy updatedPolicy) {
        Optional<InsurancePolicy> optionalPolicy = insurancePolicyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            InsurancePolicy policy = optionalPolicy.get();

            policy.setName(updatedPolicy.getName());
            policy.setDescription(updatedPolicy.getDescription());
            policy.setCoverage(updatedPolicy.getCoverage());
            policy.setPrice(updatedPolicy.getPrice());

            insurancePolicyRepository.save(policy);
        }

        return "redirect:/admin/manage-policies";
    }

    // ✅ Delete a policy
    @GetMapping("/delete/{id}")
    public String deletePolicy(@PathVariable Long id) {
        if (insurancePolicyRepository.existsById(id)) {
            insurancePolicyRepository.deleteById(id);
        }
        return "redirect:/admin/manage-policies";
    }
}
