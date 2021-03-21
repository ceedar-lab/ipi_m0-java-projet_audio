package com.audioproject.web.repository;

import com.audioproject.web.model.Artist;
import com.audioproject.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Boolean existsByArtistAndTitle(Optional<Artist> artist, String albumTitle);
}
