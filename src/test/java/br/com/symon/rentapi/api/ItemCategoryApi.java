package br.com.symon.rentapi.api;

import br.com.symon.rentapi.model.ItemCategory;
import br.com.symon.rentapi.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@AllArgsConstructor
public class ItemCategoryApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TestUtils testUtils;

    public ItemCategory createValidCategory() {
        return ItemCategory.builder()
                .name("Category " + System.currentTimeMillis())
                .build();
    }

    public ItemCategory createNewCategory() throws Exception {

        var entity = createValidCategory();

        MvcResult result = mockMvc.perform(post("/api/category")
                        .header("Authorization", "Bearer " + testUtils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isCreated())
                .andReturn();

        return testUtils.parseResponse(result, ItemCategory.class);

    }
}
