package uz.uzum.financeapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.UserInfoRepository;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;


    public UserInfoDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByUsername(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }
}
