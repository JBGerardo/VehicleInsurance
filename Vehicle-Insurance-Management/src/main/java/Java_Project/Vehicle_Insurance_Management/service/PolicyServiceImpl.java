package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserPolicy;
import Java_Project.Vehicle_Insurance_Management.repository.InsurancePolicyRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserPolicyRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import Java_Project.Vehicle_Insurance_Management.model.StripeSession;
import java.math.BigDecimal;
import java.util.List;


@Service
public class PolicyServiceImpl implements PolicyService {


    private final UserRepository userRepository;
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final UserPolicyRepository userPolicyRepository;

    @Value("${stripe.secret.key}")
    private String stripeApiKey;

    public PolicyServiceImpl(UserRepository userRepository,
                             InsurancePolicyRepository insurancePolicyRepository,
                             UserPolicyRepository userPolicyRepository) {
        this.userRepository = userRepository;
        this.insurancePolicyRepository = insurancePolicyRepository;
        this.userPolicyRepository = userPolicyRepository;
    }

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

    @Override
    public List<InsurancePolicy> getPurchasedPolicies(String username) {
        User user = userRepository.findByUsername(username);
        List<UserPolicy> userPolicies = userPolicyRepository.findByUserId(user.getId());

        return userPolicies.stream()
                .map(UserPolicy::getPolicy)
                .toList(); // or .collect(Collectors.toList()) for Java 8 compatibility
    }


    @Override
    public StripeSession createStripeSession(String username, Long policyId) throws StripeException {
        // ✅ Set your secret key
        Stripe.apiKey = "sk_test_51R5qk1DuFNdRlr0J7Auoe8wAfnAtoouEQFX1uqmMf4iBruMbzsmnuJ5ZHIJIlZo5VWpocB7Q6cnoBF6mJ0du7qez00sO4sF5qS"; // Replace with your actual key

        // ✅ Fetch policy
        InsurancePolicy policy = insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // Convert price to cents
        long amount = policy.getPrice().multiply(BigDecimal.valueOf(100)).longValue();

        // ✅ Create Stripe session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/user/payment/success?policyId=" + policyId)
                .setCancelUrl("http://localhost:8080/user/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(policy.getName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        // ✅ Create session
        Session session = Session.create(params);

        // ✅ Return our StripeSession wrapper
        return new StripeSession(session.getId(), session.getUrl(), amount);

    }



}