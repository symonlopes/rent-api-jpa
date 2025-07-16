package br.com.symon.rentapi.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import br.com.symon.rentapi.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.symon.rentapi.service.ItemService;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Item> save(@RequestBody @Valid Item item){
        log.debug("Creating a new item [{}] ", item);
        var savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }
}
