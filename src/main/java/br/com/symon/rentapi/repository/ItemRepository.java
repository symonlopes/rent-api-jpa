package br.com.symon.rentapi.repository;

import br.com.symon.rentapi.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends CrudRepository<Item, UUID> {
}
