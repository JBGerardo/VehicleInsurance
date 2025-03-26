package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.UserPolicy;
import Java_Project.Vehicle_Insurance_Management.repository.UserPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/view-payments")
public class AdminPaymentController {

    @Autowired
    private UserPolicyRepository userPolicyRepository;

    @GetMapping
    public String viewPayments(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<UserPolicy> paymentRecords;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            paymentRecords = userPolicyRepository.findAll().stream()
                    .filter(p -> p.getUser() != null &&
                            p.getUser().getMember() != null &&
                            (p.getUser().getMember().getFirstName() + " " + p.getUser().getMember().getLastName())
                                    .toLowerCase().contains(searchQuery.toLowerCase()))
                    .toList();
        } else {
            paymentRecords = userPolicyRepository.findAll();
        }

        model.addAttribute("paymentRecords", paymentRecords);
        model.addAttribute("searchQuery", searchQuery);

        return "Administrator/ManagePayment/manage-payment-table";
    }

}
