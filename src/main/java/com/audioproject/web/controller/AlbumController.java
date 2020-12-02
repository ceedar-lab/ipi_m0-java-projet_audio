package com.audioproject.web.controller;

import com.audioproject.web.model.Album;
import com.audioproject.web.model.Artist;
import com.audioproject.web.repository.AlbumRepository;
import com.audioproject.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;

    /**
     * Ajoute un album dans la base de données. L'album est obligatoirement rattaché à un artiste.
     * @param album
     * @return Album. id, title et artist.
     * @throws SQLIntegrityConstraintViolationException si l'utilisateur valide alors que le nom de l'album est null (ou qu'il commence par un espace).
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album addAlbum(
            @RequestBody Album album) throws SQLIntegrityConstraintViolationException {
        String title = album.getTitle();
        Artist artist = album.getArtist();
        if (!title.matches("\\S.*")) {
            throw new SQLIntegrityConstraintViolationException();
        }
        if (albumRepository.existsByArtistAndTitle(artist, title)) {
            throw new EntityExistsException("L'album intitulé \"" +title+ "\" est déjà enregistré.");
        }
        return albumRepository.save(album);
    }

    /**
     * Supprime un album.
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteAlbum(
            @PathVariable Integer id) {
        albumRepository.deleteById(id);
    }
}
