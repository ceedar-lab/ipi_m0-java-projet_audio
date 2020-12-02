package com.audioproject.web.repository;

import com.audioproject.web.model.Artist;
import com.audioproject.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Boolean existsByArtistAndTitle(Artist artist, String albumTitle);
}
