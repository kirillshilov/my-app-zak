package com.example.application.data.service;

import com.example.application.data.entity.Zakazi;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ZakaziService {

    private final ZakaziRepository repository;

    public ZakaziService(ZakaziRepository repository) {
        this.repository = repository;
    }

    public Optional<Zakazi> get(Long id) {
        return repository.findById(id);
    }

    public Zakazi update(Zakazi entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Zakazi> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Zakazi> list(Pageable pageable, Specification<Zakazi> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
