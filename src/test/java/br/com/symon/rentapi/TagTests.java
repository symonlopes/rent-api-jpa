package br.com.symon.rentapi;

import br.com.symon.rentapi.api.TagApi;
import br.com.symon.rentapi.config.TestSetup;
import br.com.symon.rentapi.model.Tag;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;
    @Autowired
    private TagApi tagApi;

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
    public void shouldCreateATagSuccessfully() throws Exception {

        var entity = tagApi.createValidTag();

        MvcResult result = mockMvc.perform(post("/api/tag")
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isCreated())
                .andReturn();

        var returnedItem = utils.parseResponse(result, Tag.class);

        assertNotNull(returnedItem.getId(), "Id must not be null");
    }

}
