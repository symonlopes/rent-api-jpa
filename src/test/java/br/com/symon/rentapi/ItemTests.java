package br.com.symon.rentapi;

import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.utils.TestUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
class ItemTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;
    @Autowired
    private br.com.symon.rentapi.api.ItemApi itemApi;
//	@Autowired
//	private CategoryApi categoryApi;
//	@Autowired
//	private TagApi tagApi;

    @Test
    public void shouldCreateItemSuccessfully() throws Exception {

//		var category = categoryApi.createNewCategory();
//
//		var tag_1 = tagApi.createNewTag();
//		var tag_2 = tagApi.createNewTag();

        var item = itemApi.mockItem();

//		item.setCategoryId(category.getId());
//		item.getTags().add(TagRef.builder().tagId(tag_1.getId()).build());
//		item.getTags().add(TagRef.builder().tagId(tag_2.getId()).build());

        MvcResult result = mockMvc.perform(post("/api/item")
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn();

        var returnedItem = utils.parseResponse(result, Item.class);

        assertNotNull(returnedItem.getId(), "Item IOD must not be null");
        assertEquals(item.getName(), returnedItem.getName(), "Name must match");
        assertEquals(item.getDetails(), returnedItem.getDetails(), "Details must match");
        //assertEquals(2, item.getTags().size(), "Item must have two tags");
        //assertEquals(category.getId(), item.getCategoryId(), "Category ID must match");


    }

}
