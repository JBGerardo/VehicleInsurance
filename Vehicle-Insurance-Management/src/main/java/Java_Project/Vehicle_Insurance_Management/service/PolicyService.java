package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;

public interface PolicyService {
    boolean isPolicyPurchased(String username, Long policyId);

    InsurancePolicy getPolicyById(Long policyId);

    boolean purchasePolicy(String username, Long policyId);
}
