package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Simulated service — adjust if you're using Spring Security for auth
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor getLoggedInVendor() {
        // TEMP: Fetch a hardcoded vendor (for demo/testing).
        // In real-world use, retrieve vendor by email/login context.
        return vendorRepository.findById(1L).orElse(null);
    }
}
