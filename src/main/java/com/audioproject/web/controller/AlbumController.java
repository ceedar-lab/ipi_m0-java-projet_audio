package com.audioproject.web.controller;

import com.audioproject.web.model.Album;
import com.audioproject.web.model.Artist;
import com.audioproject.web.repository.AlbumRepository;
import com.audioproject.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;

    /**
     * Ajoute un album dans la base de données. L'album est obligatoirement rattaché à un artiste.
     * @param id
     * @param title
     * @param album
     * @return RedirectView. Redirection vers la page détail de l'artiste.
     * @error 400 si le titre de l'album entré est null ou si sa longueur dépasse 100 caractères.
     * @error 409 si l'album existe déjà.
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView addAlbum(
            @RequestParam("artistId") Integer id,
            @RequestParam("title") String title,
            Album album) throws SQLIntegrityConstraintViolationException {
        Optional<Artist> artistOptional = artistRepository.findById(id);
        if (title.equals("") || title.length() > 100)
            throw new SQLIntegrityConstraintViolationException();
        if (albumRepository.existsByArtistAndTitle(artistOptional, title))
            throw new EntityExistsException();
        album.setArtist(artistOptional.get());
        album.setTitle(title);
        albumRepository.save(album);
        return new RedirectView("/artists/" + id);
    }

    /**
     * Supprime un album de la base de donnée.
     * @param id
     * @return RedirectView. Redirection vers la page de détails de l'artiste mise à jour.
     * @error 404 si l'id n'est pas dans la base de données.
     * @error 405 si l'utilisateur tente de supprimer avec une methode GET.
     */
    @PostMapping(value = "/{id}/delete")
    public RedirectView deleteAlbums(
            @PathVariable("id") Integer id) {
        if (albumRepository.findById(id).isEmpty())
            throw new NoSuchElementException();
        Optional<Album> optionalAlbum =  albumRepository.findById(id);
        Integer artistId = optionalAlbum.get().getArtist().getId();
        albumRepository.deleteById(id);
        return new RedirectView("/artists/" + artistId);
    }
}