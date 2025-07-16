package br.com.symon.rentapi;

import br.com.symon.rentapi.model.User;
import br.com.symon.rentapi.service.RoleService;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Getter
@Component
@Log4j2
@AllArgsConstructor
public class TestSetup {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final RoleService roleService;


    @PostConstruct
    public void postConstruct() {
        var adminUser = User.builder().email("admin@email.com").name("Admin User").build();
        adminUser.getRoles().add("CUSTOMER");
        adminUser.getRoles().add("ADMIN");
        userService.registerNewUser(adminUser, "tempTestUser@");

        var customerUser = User.builder().email("customer@email.com").name("Customer User").build();
        customerUser.getRoles().add("CUSTOMER");
        userService.registerNewUser(customerUser, "tempTestUser@");

    }

}
