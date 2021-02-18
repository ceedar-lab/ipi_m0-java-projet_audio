package com.audioproject.web.repository;

import com.audioproject.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Page<Artist> findAllByNameContaining(String name, Pageable page);
    Optional<Artist> findByNameIgnoreCase(String name);
}
