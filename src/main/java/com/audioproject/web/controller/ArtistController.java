package com.audioproject.web.controller;

import com.audioproject.web.model.Artist;
import com.audioproject.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    /**
     * Recherche d'un artiste avec son identifiant.
     * @param id
     * @return Optional<Artist>. id et name.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Artist> getArtistById(
            @PathVariable Integer id) {
        if (artistRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("L'artiste n'a pas été trouvé.");
        }
        return artistRepository.findById(id);
    }

    /**
     * Recherche d'un artiste avec son nom.
     * @param name
     * @param page
     * @param size
     * @param sortDirection
     * @param sortProperty
     * @return Page<Artist>. La liste des artistes sous forme paginée.
     */
    @GetMapping(params = "name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Artist> getArtistByName(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortDirection,
            @RequestParam String sortProperty) {
        Page<Artist> artistList = artistRepository.findAllByNameContaining(name, PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection.toUpperCase()), sortProperty));
        if (page < 0 || page > artistList.getTotalPages() - 1) {
            throw new IllegalArgumentException("Erreur 400 : Page invalide. Veuillez entrer une valeur entre 0 et " +(artistList.getTotalPages() - 1)+ ".");
        }
        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("Erreur 400 : Le paramètre size doit être compris entre 0 et 50");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
            throw new IllegalArgumentException("Erreur 400 : Le paramètre sortDirection doit être 'ASC' ou 'DESC'.");
        }
        return artistList;
    }

    /**
     * Affiche la liste complète des artistes.
     * @param page
     * @param size
     * @param sortProperty
     * @param sortDirection
     * @return Page<Artist>. La liste des artistes sous forme paginée.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Artist> getArtists(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection) {
        Page<Artist> artistList = artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection.toUpperCase()), sortProperty));
        if (page < 0 || page > artistList.getTotalPages() - 1) {
            throw new IllegalArgumentException("Erreur 400 : Page invalide. Veuillez entrer une valeur entre 0 et " +(artistList.getTotalPages() - 1)+ ".");
        }
        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("Erreur 400 : Le paramètre size doit être compris entre 0 et 50");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
            throw new IllegalArgumentException("Erreur 400 : Le paramètre sortDirection doit être 'ASC' ou 'DESC'.");
        }
        return artistList;
    }

    /**
     * Enregistre un nouvel artiste dans la base de données.
     * @param artist
     * @return Artist. id et name.
     * @throws SQLIntegrityConstraintViolationException si l'utilisateur valide alors que le nom de l'artiste est null (ou qu'il commence par un espace).
     */
    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist createArtist(
            @RequestBody Artist artist) throws SQLIntegrityConstraintViolationException {
        String name = artist.getName();
        if (!name.matches("\\S.*")) {
            throw new SQLIntegrityConstraintViolationException();
        }
        if (artistRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new EntityExistsException("L'artiste \"" +name+ "\" est déjà enregistré.");
        }
        return artistRepository.save(artist);
    }

    /**
     * Mets à jour le nom de l'artiste.
     * @param artist
     * @param id
     * @return Artist. id et name.
     * @throws SQLIntegrityConstraintViolationException si l'utilisateur valide alors que le nom de l'artiste est null (ou qu'il commence par un espace).
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist updateArtist(
            @RequestBody Artist artist,
            @PathVariable Integer id) throws SQLIntegrityConstraintViolationException {
        if (!artist.getName().matches("\\S.*")) {
            throw new SQLIntegrityConstraintViolationException();
        }
        return artistRepository.save(artist);
    }

    /**
     * Supprime un artiste et les albums qui lui sont attribués.
     * @param id
     */
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteArtist(
            @PathVariable Integer id) {
        artistRepository.deleteById(id);
    }
}
