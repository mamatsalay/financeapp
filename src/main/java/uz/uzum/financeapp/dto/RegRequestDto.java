package uz.uzum.financeapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50,message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Roles are required. Can be only [\"USER\"], [\"ADMIN\"] or [\"USER, ADMIN\"] ")
    private Set<String> roles;

}
