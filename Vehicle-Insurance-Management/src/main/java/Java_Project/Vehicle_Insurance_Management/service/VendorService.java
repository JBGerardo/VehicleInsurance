package Java_Project.Vehicle_Insurance_Management.service;

import Java_Project.Vehicle_Insurance_Management.model.Vendor;

public interface VendorService {
    Vendor getLoggedInVendor(); // retrieves the current vendor, possibly by session or mock
}
