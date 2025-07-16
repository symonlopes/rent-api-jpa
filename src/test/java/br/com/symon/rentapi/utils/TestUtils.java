package br.com.symon.rentapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import br.com.symon.rentapi.service.TokenService;
import br.com.symon.rentapi.service.UserService;

@Getter
@Component
@AllArgsConstructor
public class TestUtils {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;

    public <T> T  parseResponse(MvcResult result, Class<T> errorResponseClass) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), errorResponseClass);
    }

    public String createAdminJwtToken() {
        return tokenService.generateToken(TestConstants.ADMIN_EMAIL, TestConstants.ADMIN_PASSWORD);
    }

    public String createCustomerJwtToken() {
        return tokenService.generateToken(TestConstants.CUSTOMER_EMAIL,TestConstants.CUSTOMER_PASSWORD);
    }

}