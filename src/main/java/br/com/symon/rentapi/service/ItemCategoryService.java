package br.com.symon.rentapi.service;

import br.com.symon.rentapi.error.ResourceNotFoundException;
import br.com.symon.rentapi.model.ItemCategory;
import br.com.symon.rentapi.repository.ItemCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Log4j2
public class ItemCategoryService {

    private final ItemCategoryRepository repository;

    public ItemCategory create(ItemCategory entity) {
        return repository.save(entity);
    }

    public Optional<ItemCategory> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("CATEGORY not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public ItemCategory update(ItemCategory entity) {
        log.debug("Attempting to update CATEGORY with id: {}", entity);
        return repository.save(entity);
    }


}
