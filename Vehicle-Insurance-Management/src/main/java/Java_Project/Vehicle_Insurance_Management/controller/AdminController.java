package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.Member;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserRole;
import Java_Project.Vehicle_Insurance_Management.repository.ClaimRepository;
import Java_Project.Vehicle_Insurance_Management.repository.MemberRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;  // Autowire MemberRepository
    @Autowired
    private ClaimRepository claimRepository;  // Claim repository

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user != null && user.isAdmin()) {
            // Add the admin user details to the model
            model.addAttribute("admin", user.getMember());
            return "Administrator/admin-profile"; // Return to the admin profile page
        }

        // Redirect to an error page if the user is not an admin
        return "redirect:/error";  // You can customize this to redirect to an appropriate page
    }

    @GetMapping("/manage-members")
    public String manageMembers(@RequestParam(name = "search", required = false) String searchQuery, Model model) {
        List<Member> members;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Calls the method defined in MemberRepository to search by first name
            members = memberRepository.findByFirstNameContainingIgnoreCase(searchQuery);
        } else {
            // If no search query, return all members
            members = memberRepository.findAll();
        }

        model.addAttribute("searchQuery", searchQuery);  // Pass search query to the view
        model.addAttribute("filteredMembers", members);  // Pass filtered members to the view
        return "Administrator/ManageMember/manage-member-table";  // Return to the manage members page
    }

    @GetMapping("/manage-members/edit/{id}")
    public String editMember(@PathVariable("id") Long id, Model model) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        model.addAttribute("member", member);
        return "Administrator/ManageMember/manage-member-edit";
    }

    @PostMapping("/manage-members/edit/{id}")
    public String updateMember(@PathVariable("id") Long memberId, @ModelAttribute("member") Member member, Model model) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        // Update fields
        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhone(member.getPhone());
        existingMember.setStreet(member.getStreet());
        existingMember.setCity(member.getCity());
        existingMember.setProvince(member.getProvince());
        existingMember.setPostalCode(member.getPostalCode());

        memberRepository.save(existingMember);

        return "redirect:/admin/manage-members"; // Redirect back to the manage users page
    }


    @GetMapping("/manage-members/delete/{id}")
    public String deleteMember(@PathVariable("id") Long memberId, Model model) {
        // Find the member by ID
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        // Check if there are any related claims
        if (member.getClaims() != null && !member.getClaims().isEmpty()) {
            // Optionally, delete related claims first
            for (Claim claim : member.getClaims()) {
                claimRepository.delete(claim);  // Deleting claims associated with the member
            }
        }

        // Delete the member
        memberRepository.delete(member);

        // Add a success message to the model
        model.addAttribute("successMessage", "Member deleted successfully");

        // Redirect to the manage members page
        return "redirect:/admin/manage-members";  // Redirect back to the manage members page
    }

    @GetMapping("/manage-users")
    public String manageUsers(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<User> filteredUsers;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            filteredUsers = userRepository.findByUsernameContainingIgnoreCase(searchQuery);
        } else {
            filteredUsers = userRepository.findAll();
        }

        model.addAttribute("filteredUsers", filteredUsers);
        model.addAttribute("searchQuery", searchQuery);

        return "Administrator/ManageUser/manage-user-table";
    }

    // Show the edit form
    @GetMapping("/manage-users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "Administrator/ManageUser/manage-user-edit"; // match your folder structure
        } else {
            return "redirect:/admin/manage-users"; // or a 404 page if user not found
        }
    }

    @PostMapping("/manage-users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());

            // Sync isAdmin and role together
            boolean isAdmin = updatedUser.isAdmin();
            user.setAdmin(isAdmin);

            if (isAdmin) {
                user.setRole(UserRole.ROLE_ADMIN);
            } else {
                // Keep the role from form input, or default to ROLE_USER if not set
                UserRole role = updatedUser.getRole() != null ? updatedUser.getRole() : UserRole.ROLE_USER;
                user.setRole(role);
            }

            userRepository.save(user);
        }

        return "redirect:/admin/manage-users";
    }


    @GetMapping("/manage-users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        return "redirect:/admin/manage-users";
    }
}


