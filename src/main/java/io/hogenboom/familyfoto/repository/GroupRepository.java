package io.hogenboom.familyfoto.repository;

import io.hogenboom.familyfoto.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, UUID>,
        CrudRepository<Group, UUID> {

    @Override
    Iterable<Group> findAll();

    Set<Group> findByIdIn(List<UUID> ids);
}
