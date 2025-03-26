package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUserId(Long userId);
    List<Claim> findByPolicyId(Long policyId);
    public void deleteByUserId(Long userId);

}