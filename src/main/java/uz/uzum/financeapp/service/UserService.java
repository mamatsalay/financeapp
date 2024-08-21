package uz.uzum.financeapp.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.dto.AuthRequestDto;
import uz.uzum.financeapp.dto.AuthResponseDto;
import uz.uzum.financeapp.dto.RegRequestDto;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.UserInfoRepository;

import java.util.HashSet;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(AuthenticationManager authenticationManager,
                       UserInfoRepository userInfoRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String registerUser(RegRequestDto regRequestDto) {
        if(userInfoRepository.existsByUsername(regRequestDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(regRequestDto.getUsername());
        userInfo.setPassword(passwordEncoder.encode(regRequestDto.getPassword()));
        userInfo.setRoles(new HashSet<>(regRequestDto.getRoles()));
        userInfoRepository.save(userInfo);

        return "User registered successfully";
    }

    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        return new AuthResponseDto(jwt);
    }

}
