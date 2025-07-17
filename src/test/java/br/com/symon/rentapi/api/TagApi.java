package br.com.symon.rentapi.api;

import br.com.symon.rentapi.model.Tag;
import br.com.symon.rentapi.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TagApi {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;

    public Tag createValidTag() {
        var randomThing =  System.currentTimeMillis();
        return Tag.builder()
                .name("Tag Name " + randomThing)
                .value("tag_value_" + randomThing)
                .build();
    }

    public Tag createNewTag() throws Exception {

        var entity = createValidTag();

        MvcResult result = mockMvc.perform(post("/api/tag")
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isCreated())
                .andReturn();

        return utils.parseResponse(result, Tag.class);
    }

}
