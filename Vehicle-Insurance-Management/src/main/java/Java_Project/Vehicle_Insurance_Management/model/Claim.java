package Java_Project.Vehicle_Insurance_Management.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(length = 1000)
    private String description;

    private String status;

    private LocalDate claimDate;

    private String vehicleDetails;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;


    @Column(name = "vendor_status")
    private String vendorStatus;

    // 🔗 Relationship to Member
    @ManyToOne
    @JoinColumn(name = "member_id") // Update the foreign key column name to member_id
    private Member member;

    // 🔗 Relationship to User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔗 Relationship to Policy
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private InsurancePolicy policy;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InsurancePolicy getPolicy() {
        return policy;
    }

    public void setPolicy(InsurancePolicy policy) {
        this.policy = policy;
    }

    public String getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(String vendorStatus) {
        this.vendorStatus = vendorStatus;
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }


}