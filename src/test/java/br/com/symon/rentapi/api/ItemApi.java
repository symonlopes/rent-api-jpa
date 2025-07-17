package br.com.symon.rentapi.api;

import br.com.symon.rentapi.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import br.com.symon.rentapi.model.ItemImage;
import br.com.symon.rentapi.model.Item;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Getter
@Component
@AllArgsConstructor
public class ItemApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TestUtils testUtils;

    public Item mockItem() {

        var validItem = Item.builder()
                .name("Item Name")
                .details("Item Details")
                .build();

        validItem.getImages().add(ItemImage.builder().url("https://dummyimage.com/600x400/c6c6c6/000000.jpg")
                .build());

        return validItem;
    }

    public Item createNewItem() throws Exception {

        var item = mockItem();

        MvcResult result = mockMvc.perform(post("/api/items")
                        .header("Authorization", "Bearer " + testUtils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUtils.getObjectMapper().writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn();

        return testUtils.parseResponse(result, Item.class);
    }
}
