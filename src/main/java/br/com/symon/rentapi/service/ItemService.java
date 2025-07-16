package br.com.symon.rentapi.service;

import br.com.symon.rentapi.error.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import br.com.symon.rentapi.model.Item;
import org.springframework.stereotype.Service;
import br.com.symon.rentapi.repository.ItemRepository;

@AllArgsConstructor
@Service
@Log4j2
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        var inserted = itemRepository.save(item);

        return itemRepository.findById(inserted.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + inserted.getId()));
    }
}
