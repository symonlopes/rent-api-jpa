package br.com.symon.rentapi.config;

import br.com.symon.rentapi.model.User;
import br.com.symon.rentapi.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TestSetup {

    @Autowired
    private UserService userService;

    public void beforeAll() {
        log.info(">>> TEST BEGIN");

        var adminUser = User.builder().email("admin@email.com").name("Admin User").build();
        adminUser.getRoles().add("CUSTOMER");
        adminUser.getRoles().add("ADMIN");
        userService.registerNewUser(adminUser, "tempTestUser@");

        var customerUser = User.builder().email("customer@email.com").name("Customer User").build();
        customerUser.getRoles().add("CUSTOMER");
        userService.registerNewUser(customerUser, "tempTestUser@");
    }

    public void affterAll() {
        userService.deleteUserByEmail("admin@email.com");
        userService.deleteUserByEmail("customer@email.com");
    }

}
