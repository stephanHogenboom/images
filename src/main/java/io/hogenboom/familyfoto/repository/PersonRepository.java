package io.hogenboom.familyfoto.repository;

import io.hogenboom.familyfoto.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, UUID>,
        CrudRepository<Person, UUID> {

    @Override
    Iterable<Person> findAll();

    Set<Person> findByIdIn(List<UUID> ids);
}
