package Java_Project.Vehicle_Insurance_Management.repository;

import Java_Project.Vehicle_Insurance_Management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // You can add custom query methods here if needed
    // This method will search for members by their first name, ignoring case
    List<Member> findByFirstNameContainingIgnoreCase(String firstName);

}
