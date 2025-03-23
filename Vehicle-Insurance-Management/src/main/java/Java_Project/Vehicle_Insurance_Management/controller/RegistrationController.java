package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Member;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.repository.MemberRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // STEP 1: Handle member info form
    @PostMapping("/register-step1")
    public String saveMember(@ModelAttribute Member member, Model model) {
        Member savedMember = memberRepository.save(member);
        model.addAttribute("memberId", savedMember.getId());
        return "register-step2"; // Go to second page for username & password
    }

    // STEP 2: Handle user login credentials
    @PostMapping("/register-final")
    public String saveUser(
            @RequestParam("memberId") Long memberId,
            @RequestParam("username") String username,
            @RequestParam("password") String rawPassword,
            Model model) {

        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "error";
        }

        User user = new User();
        user.setUsername(username);

        // ✅ Hash the password before saving
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);

        user.setMember(member);
        user.setAdmin(false); // default to regular user

        userRepository.save(user);

        return "redirect:/login";
    }

    // Optional: Display Step 1 form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "register-step1";
    }

    // Optional: Display login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "loginView";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // looks for forgot-password.html
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String newPassword,
            Model model) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            model.addAttribute("error", "Username not found.");
            return "forgot-password";
        }

        // Hash the new password before saving
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        model.addAttribute("message", "Password updated successfully. You can now log in.");
        return "loginView"; // redirect to login page after successful update
    }


}
