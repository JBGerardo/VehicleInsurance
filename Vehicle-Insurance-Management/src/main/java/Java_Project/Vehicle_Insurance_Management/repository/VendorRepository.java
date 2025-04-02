package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByNameContainingIgnoreCase(String name);
    List<Vendor> findByStatusIgnoreCase(String status);
    List<Vendor> findAll();  // Retrieve all vendors


}