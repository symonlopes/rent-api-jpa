package br.com.symon.rentapi.controller;

import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.model.Tag;
import br.com.symon.rentapi.service.TagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
@Log4j2
public class TagController {


    private final TagService service;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Tag> create(@RequestBody @Valid Tag entity){
        log.debug("Creating a new TAG [{}] ", entity);
        var savedItem = service.create(entity);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public ResponseEntity<Tag> getById(@PathVariable UUID id) {
        log.debug("Fetching TAG with id [{}]", id);
        return service.findById(id)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Item> delete(@PathVariable UUID id){
        log.debug("Deleting TAG with id [{}]", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Tag> update(@RequestBody @Valid Tag entity) {
        var updatedItem = service.update(entity);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }


}
