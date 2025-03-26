package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here if needed, like:
    User findByUsername(String username);
    List<User> findByMemberFirstNameContainingIgnoreCase(String firstName);
    List<User> findByUsernameContainingIgnoreCase(String username);


}