package Java_Project.Vehicle_Insurance_Management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_policy")
public class UserPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private InsurancePolicy policy;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @PrePersist
    protected void onPurchase() {
        this.purchaseDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public InsurancePolicy getPolicy() { return policy; }

    public void setPolicy(InsurancePolicy policy) { this.policy = policy; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }

    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
}
