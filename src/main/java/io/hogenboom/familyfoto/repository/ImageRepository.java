package io.hogenboom.familyfoto.repository;

import io.hogenboom.familyfoto.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, UUID>,
        CrudRepository<Image, UUID> {

    @Override
    Iterable<Image> findAll();
}
