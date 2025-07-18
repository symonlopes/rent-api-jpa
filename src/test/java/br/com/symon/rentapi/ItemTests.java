package br.com.symon.rentapi;

import br.com.symon.rentapi.api.ItemApi;
import br.com.symon.rentapi.api.ItemCategoryApi;
import br.com.symon.rentapi.api.TagApi;
import br.com.symon.rentapi.config.TestSetup;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.model.ItemImage;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils utils;
    @Autowired
    private ItemApi itemApi;
    @Autowired
    private ItemCategoryApi itemCategoryApi;
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
    public void shouldCreateItemSuccessfully() throws Exception {

        var item = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        item.setCategory(category);

        var result = itemApi.postItem(item)
                .andExpect(status().isCreated())
                .andReturn();

        var returnedItem = utils.parseResponse(result, Item.class);

        assertNotNull(returnedItem.getId(), "Item IOD must not be null");
        assertEquals(item.getName(), returnedItem.getName(), "Name must match");
        assertEquals(item.getDetails(), returnedItem.getDetails(), "Details must match");
        assertEquals(category.getId(), returnedItem.getCategory().getId(), "Category ID must match");

    }

    @Test
    public void shouldCreateItemWithTags() throws Exception {
        var item = itemApi.mockItem();

        var tag_1 = tagApi.createNewTag();
        var tag_2 = tagApi.createNewTag();
        item.getTags().add(tag_1);
        item.getTags().add(tag_2);

        var category = itemCategoryApi.createNewCategory();
        item.setCategory(category);

        var result = itemApi.postItem(item)
                .andExpect(status().isCreated())
                .andReturn();

        var returnedItem = utils.parseResponse(result, Item.class);

        assertNotNull(returnedItem.getId(), "Item IOD must not be null");
        assertEquals(2, returnedItem.getTags().size(), "Item must have two tags");

    }

    @Test
    public void shouldNotCreateItemWithoutAtLeastOneImage() throws Exception {

        var item = itemApi.mockItem();
        var category = itemCategoryApi.createNewCategory();

        item.setCategory(category);
        item.setImages(null);

        itemApi.postItem(item)
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void shouldCreateItemWithoutDetails() throws Exception {

        var itemToCreate = itemApi.mockItem();
        itemToCreate.setDetails(null);

        var category = itemCategoryApi.createNewCategory();
        itemToCreate.setCategory(category);

        itemApi.postItem(itemToCreate)
                .andExpect(status().isCreated())
                .andReturn();
    }


    @Test
    public void shouldNotCreateItemWithTooShortName() throws Exception {

        Item itemToCreate = itemApi.mockItem();
        itemToCreate.setName("A");

        var category = itemCategoryApi.createNewCategory();
        itemToCreate.setCategory(category);

        itemApi.postItem(itemToCreate)
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void shouldNotCreateItemWithoutName() throws Exception {

        Item itemToCreate = itemApi.mockItem();
        itemToCreate.setName("");

        var category = itemCategoryApi.createNewCategory();
        itemToCreate.setCategory(category);

        itemApi.postItem(itemToCreate)
                .andExpect(status().isBadRequest())
                .andReturn();

    }


    @Test
    public void shouldGetItemSuccessfully() throws Exception {

        var itemToCreate = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        itemToCreate.setCategory(category);

        var result = itemApi.postItem(itemToCreate)
                .andExpect(status().isCreated())
                .andReturn();

        var createdItem = utils.parseResponse(result, Item.class);

        log.info("Created Item: {}", createdItem);

        var getResult = mockMvc.perform(
                        get("/api/item/" + createdItem.getId())
                                .header("Authorization", "Bearer " + utils.createAdminJwtToken())
                )
                .andExpect(status().isOk())
                .andReturn();

        var fetchedItem = utils.parseResponse(getResult, Item.class);

        assertEquals(createdItem.getId(), fetchedItem.getId());
        assertEquals(itemToCreate.getName(), fetchedItem.getName());
        assertEquals(itemToCreate.getDetails(), fetchedItem.getDetails());
        assertEquals(category, fetchedItem.getCategory());

    }

    @Test
    public void shouldReturn404WithInvalidId() throws Exception {
        mockMvc.perform(get("/api/item/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void shouldDeleteItemSuccessfully() throws Exception {
        var itemToCreate = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        itemToCreate.setCategory(category);

        var createResult = itemApi.postItem(itemToCreate)
                .andExpect(status().isCreated())
                .andReturn();

        var createdItem = utils.parseResponse(createResult, Item.class);
        log.info("Item created for deletion with ID: {}", createdItem.getId());

        mockMvc.perform(delete("/api/item/" + createdItem.getId())
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isNoContent());

        log.info("Delete request sent for item ID: {}", createdItem.getId());

        mockMvc.perform(get("/api/item/" + createdItem.getId())
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isNotFound());

        log.info("Verified that item ID {} is no longer found.", createdItem.getId());
    }

    @Test
    public void shouldReturn404WhenDeletingInvalidItem() throws Exception {
        var randomId = UUID.randomUUID();
        mockMvc.perform(delete("/api/item/" + randomId).header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isNotFound());
        log.info("Verified that deleting a non-existent item with ID {} returns 404.", randomId);
    }

    @Test
    public void shouldUpdateItemSuccessfully() throws Exception {
        var initialItem = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        initialItem.setCategory(category);

        var createResult = itemApi.postItem(initialItem)
                .andExpect(status().isCreated())
                .andReturn();

        var createdItem = utils.parseResponse(createResult, Item.class);

        createdItem.setName("EDITED NAME");
        createdItem.setDetails("EDITED DETAILS");
        createdItem.getImages().add(ItemImage.builder().url("https://new-image.com/second-image.jpg").build());

        var updateResult = mockMvc.perform(put("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(createdItem))
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isOk())
                .andReturn();

        var updatedItem = utils.parseResponse(updateResult, Item.class);

        assertEquals(createdItem.getId(), updatedItem.getId(), "ID should not change");
        assertEquals(createdItem.getName(), updatedItem.getName());
        assertEquals(createdItem.getDetails(), updatedItem.getDetails());
    }


    @Test
    public void shouldUpdateItemAddingOneTag() throws Exception {
        var initialItem = itemApi.mockItem();

        var category = itemCategoryApi.createNewCategory();
        initialItem.setCategory(category);

        var tag_1 = tagApi.createNewTag();
        var tag_2 = tagApi.createNewTag();
        initialItem.getTags().add(tag_1);
        initialItem.getTags().add(tag_2);

        var createResult = itemApi.postItem(initialItem)
                .andExpect(status().isCreated())
                .andReturn();

        var createdItem = utils.parseResponse(createResult, Item.class);
        log.info("Initial item created: {}", createdItem);

        var tag_3 = tagApi.createNewTag();
        createdItem.getTags().add(tag_3);
        createdItem.getImages().add(ItemImage.builder().url("https://new-image.com/second-image.jpg").build());

        var updateResult = mockMvc.perform(put("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(utils.getObjectMapper().writeValueAsString(createdItem))
                        .header("Authorization", "Bearer " + utils.createAdminJwtToken()))
                .andExpect(status().isOk())
                .andReturn();

        var updatedItem = utils.parseResponse(updateResult, Item.class);

        assertEquals(3, updatedItem.getTags().size(), "ID should not change");
    }

}
