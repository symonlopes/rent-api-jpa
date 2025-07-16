package br.com.symon.rentapi.service;

import br.com.symon.rentapi.model.Role;
import br.com.symon.rentapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
public class RoleService {
    private final RoleRepository repository;
    public Iterable<Role> findAll() {
        return repository.findAll();
    }
}
