package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserPolicy;
import Java_Project.Vehicle_Insurance_Management.repository.UserPolicyRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPolicyRepository userPolicyRepository;

    @GetMapping("/user/payment")
    public String showPaymentHistory(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<UserPolicy> payments = userPolicyRepository.findByUserId(user.getId());
            model.addAttribute("payments", payments);
        } else {
            model.addAttribute("payments", Collections.emptyList());
        }

        return "Member/Payment/payment-history";
    }

}