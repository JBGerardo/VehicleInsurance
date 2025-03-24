package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.InsurancePolicy;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPolicyRepository extends JpaRepository<UserPolicy, Long> {
    List<UserPolicy> findByUser(User user);
    boolean existsByUserIdAndPolicyId(Long userId, Long policyId);
    List<UserPolicy> findByUserId(Long userId);
}
