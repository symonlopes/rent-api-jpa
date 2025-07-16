package br.com.symon.rentapi.repository;

import br.com.symon.rentapi.model.ItemCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, UUID> {

}
