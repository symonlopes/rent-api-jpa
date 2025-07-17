package br.com.symon.rentapi.service;

import br.com.symon.rentapi.error.ResourceNotFoundException;
import br.com.symon.rentapi.model.Item;
import br.com.symon.rentapi.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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


    public Optional<Item> findById(UUID id) {
        return itemRepository.findById(id);
    }

    public void deleteById(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

    public Item update(Item itemToUpdate) {
        log.debug("Attempting to update ITEM with id: {}", itemToUpdate);
        return itemRepository.save(itemToUpdate);
    }

}
