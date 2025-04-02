package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUserId(Long userId);
    List<Claim> findByPolicyId(Long policyId);
    public void deleteByUserId(Long userId);
    List<Claim> findByTypeContainingIgnoreCase(String type);
    List<Claim> findByVendor(Vendor vendor);
    List<Claim> findByVendorId(Long vendorId);
    List<Claim> findAll();  // Retrieve all claims

}