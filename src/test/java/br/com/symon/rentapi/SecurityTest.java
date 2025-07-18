package br.com.symon.rentapi;

import br.com.symon.rentapi.api.ItemApi;
import br.com.symon.rentapi.api.ItemCategoryApi;
import br.com.symon.rentapi.config.TestSetup;
import br.com.symon.rentapi.utils.TestUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;
    @Autowired
    private ItemApi itemApi;
    @Autowired
    private ItemCategoryApi itemCategoryApi;

    @Autowired
    private TestSetup testSetup;

    @BeforeAll
    void init() {
        testSetup.beforeAll();
    }

    @AfterAll
    void cleanup() {
        testSetup.affterAll();
    }

    @Test
    public void customersShouldNotCreateItems() throws Exception {

        var item = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        item.setCategory(category);

        mockMvc.perform(post("/api/item")
                        .header("Authorization", "Bearer " + utils.createCustomerJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(item)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void customersShouldNotBeAbleToDeleteItem() throws Exception {

        var item = itemApi.createNewItem();

        mockMvc.perform(delete("/api/item/" + item.getId())
                        .header("Authorization", "Bearer " + utils.createCustomerJwtToken()))
                .andExpect(status().isForbidden());


    }
}
