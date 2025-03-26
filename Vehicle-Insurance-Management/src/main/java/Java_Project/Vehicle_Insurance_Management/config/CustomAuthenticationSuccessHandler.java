package Java_Project.Vehicle_Insurance_Management.config;

import Java_Project.Vehicle_Insurance_Management.model.User;
import Java_Project.Vehicle_Insurance_Management.model.UserRole;
import Java_Project.Vehicle_Insurance_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            UserRole role = user.getRole(); // Enum: ROLE_ADMIN, ROLE_VENDOR, ROLE_USER

            switch (role) {
                case ROLE_ADMIN:
                    response.sendRedirect("/admin/dashboard");
                    break;
                case ROLE_VENDOR:
                    response.sendRedirect("/vendor/dashboard");
                    break;
                case ROLE_USER:
                default:
                    response.sendRedirect("/user/profile");
                    break;
            }
        } else {
            // Default fallback
            response.sendRedirect("/login?error");
        }
    }
}


