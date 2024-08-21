package uz.uzum.financeapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.uzum.financeapp.dto.AuthRequestDto;
import uz.uzum.financeapp.dto.AuthResponseDto;
import uz.uzum.financeapp.dto.RegRequestDto;
import org.springframework.web.bind.annotation.*;
import uz.uzum.financeapp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegRequestDto regRequestDto) {
        try{
            String response = userService.registerUser(regRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = userService.authenticateUser(authRequestDto);
        return ResponseEntity.ok(authResponseDto);
    }

}
