package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserPolicy;
import Java_Project.Vehicle_Insurance_Management.repository.InsurancePolicyRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserPolicyRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private UserPolicyRepository userPolicyRepository;

    @Override
    public boolean isPolicyPurchased(String username, Long policyId) {
        User user = userRepository.findByUsername(username);
        return userPolicyRepository.existsByUserIdAndPolicyId(user.getId(), policyId);
    }

    @Override
    public InsurancePolicy getPolicyById(Long policyId) {
        return insurancePolicyRepository.findById(policyId).orElse(null);
    }

    @Override
    public boolean purchasePolicy(String username, Long policyId) {
        User user = userRepository.findByUsername(username);
        InsurancePolicy policy = insurancePolicyRepository.findById(policyId).orElse(null);

        if (user != null && policy != null && !isPolicyPurchased(username, policyId)) {
            UserPolicy userPolicy = new UserPolicy();
            userPolicy.setUser(user);
            userPolicy.setPolicy(policy);
            userPolicyRepository.save(userPolicy);
        }
        return false;
    }
}