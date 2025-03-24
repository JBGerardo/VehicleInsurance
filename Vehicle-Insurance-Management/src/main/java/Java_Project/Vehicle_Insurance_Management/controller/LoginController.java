package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.model.Member;
import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.repository.MemberRepository;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/user/profile")
    public String userProfile(Model model, Principal principal) {
        // Principal gives you the currently logged-in username
        User user = userRepository.findByUsername(principal.getName());

        if (user != null && user.getMember() != null) {
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("member", user.getMember());
        }

        return "Member/Profile/user-profile"; // This should match the filename in src/main/resources/templates/
    }

    @GetMapping("/user/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        Member member = user.getMember();

        model.addAttribute("member", member);
        return "Member/Profile/user-profile-edit";
    }

    @PostMapping("/user/profile/update")
    public String updateProfile(@ModelAttribute Member member, Principal principal) {
        // find the existing user
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user != null && user.getMember() != null) {
            Member existingMember = user.getMember();

            existingMember.setFirstName(member.getFirstName());
            existingMember.setLastName(member.getLastName());
            existingMember.setEmail(member.getEmail());
            existingMember.setPhone(member.getPhone());
            existingMember.setStreet(member.getStreet());
            existingMember.setCity(member.getCity());
            existingMember.setProvince(member.getProvince());
            existingMember.setPostalCode(member.getPostalCode());

            memberRepository.save(existingMember);
        }

        return "redirect:/user/profile"; // redirect back to profile page
    }



}
