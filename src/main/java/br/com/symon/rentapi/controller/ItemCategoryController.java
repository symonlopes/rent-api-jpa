package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.ItemCategory;
import br.com.symon.rentapi.service.ItemCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/category")
public class ItemCategoryController {


    private final ItemCategoryService itemCategoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ItemCategory> create(@RequestBody @Valid ItemCategory entity) {
        log.debug("Creating a new CATEGORY [{}] ", entity);
        var saved = itemCategoryService.create(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCategory> getById(@PathVariable UUID id) {
        log.debug("Fetching CATEGORY with id [{}]", id);
        return itemCategoryService.findById(id)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ItemCategory> delete(@PathVariable UUID id){
        log.debug("Deleting CATEGORY with id [{}]", id);
        itemCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ItemCategory> update(@RequestBody @Valid ItemCategory entity) {
        log.debug("Updating CATEGORY with id [{}]", entity);
        var updatedItem = itemCategoryService.update(entity);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
