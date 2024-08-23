package uz.uzum.financeapp.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.UserInfoRepository;

@Component
public class SecurityUtil {

    private final UserInfoRepository userInfoRepository;

    public SecurityUtil(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else{
            return principal.toString();
        }
    }

    public boolean isAdmin() {
        return getAuthenticatedUser().getRoles().contains("ADMIN");
    }

    public UserInfo getAuthenticatedUser() {
        String username = this.getUsernameFromSecurityContext();
        return userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
