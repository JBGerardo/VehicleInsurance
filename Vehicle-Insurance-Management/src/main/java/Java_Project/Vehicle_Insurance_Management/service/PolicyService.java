package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
// PolicyService.java
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import Java_Project.Vehicle_Insurance_Management.model.StripeSession;

public interface PolicyService {
    boolean isPolicyPurchased(String username, Long policyId);

    InsurancePolicy getPolicyById(Long policyId);

    boolean purchasePolicy(String username, Long policyId);
    StripeSession createStripeSession(String username, Long policyId) throws StripeException;
}
