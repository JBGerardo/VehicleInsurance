package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// Simulated service — adjust if you're using Spring Security for auth
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Vendor getLoggedInVendor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        return (user != null) ? user.getVendor() : null;
    }

}
