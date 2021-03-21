package com.audioproject.web.controller;

import com.audioproject.web.model.Artist;
import com.audioproject.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    /**
     * Recherche d'un artiste avec son identifiant.
     * @param id
     * @param model
     * @return String. Retourne la page detailArtist.html de l'artiste id = {id}.
     * @error 404 si l'id n'est pas dans la base de données.
     */
    @GetMapping(value = "/{id}")
    public String findArtistById(
            @PathVariable Integer id,
            final ModelMap model) {
        Optional<Artist> artistOptional = artistRepository.findById(id);
        model.put("artist", artistOptional.get());
        return "detailArtist";
    }

    /**
     * Recherche d'un artiste avec son nom.
     * @param name
     * @param page
     * @param size
     * @param sortProperty
     * @param sortDirection
     * @param model
     * @return String. Retourne la page listeArtist.html. Liste paginée des artistes qui correspondent à la recherche.
     * @error 400 si les paramètres entrés dans l'url sont incorrects.
     */
    @GetMapping(params = "name")
    public String findArtistByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            final ModelMap model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty);
        Page<Artist> artists = artistRepository.findAllByNameContaining(name, pageRequest);
        if (size > 50)
            throw new IllegalArgumentException();
        if (page + 1 > artists.getTotalPages())
            throw new IllegalArgumentException();
        model.put("artists", artists);
        model.put("pageNumber", page + 1);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("start", page * size + 1);
        model.put("end", artists.getNumberOfElements() + page * size);
        model.put("total", artists.getTotalElements());
        return "listeArtists";
    }

    /**
     * Affiche la liste complète des artistes.
     * @param page
     * @param size
     * @param sortProperty
     * @param sortDirection
     * @param model
     * @return String. Retourne la page listeArtist.html. Liste paginée dd l'ensemble des artistes de la librairie.
     * @error 400 si les paramètres entrés dans l'url sont incorrects.
     */
    @GetMapping()
    public String findArtistByName(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            final ModelMap model) {
        if (size > 50)
            throw new IllegalArgumentException();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty);
        Page<Artist> artists = artistRepository.findAll(pageRequest);
        if (size > 50)
            throw new IllegalArgumentException();
        if (page + 1 > artists.getTotalPages())
            throw new IllegalArgumentException();
        model.put("artists", artists);
        model.put("pageNumber", page + 1);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("start", page * size + 1);
        model.put("end", artists.getNumberOfElements() + page * size);
        model.put("total", artists.getTotalElements());
        return "listeArtists";
    }

    /**
     * Enregistre un nouvel artiste dans la base de données.
     * @param artist
     * @return RedirectView. Redirection vers la page de détail de l'artiste crée.
     * @error 400 si le nom de l'artiste entré est null ou si sa longueur dépasse 100 caractères.
     * @error 409 si l'artiste existe déjà.
     */
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createArtist(
            Artist artist) throws SQLIntegrityConstraintViolationException {
        if (artist.getName().equals("") || artist.getName().length() > 100)
            throw new SQLIntegrityConstraintViolationException();
        if (artistRepository.findByNameIgnoreCase(artist.getName()).isPresent())
            throw new EntityExistsException();
        artist = artistRepository.save(artist);
        return new RedirectView("/artists/" + artist.getId());
    }

    /**
     * Mets à jour le nom de l'artiste.
     * @param id
     * @param artist
     * @return RedirectView. Redirection vers la page de détails de l'artiste mise à jour.
     * @error 400 si le nom de l'artiste entré est null ou si sa longueur dépasse 100 caractères.
     * @error 409 si l'artiste existe déjà.
     */
    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateArtist(
            @PathVariable Integer id,
            Artist artist) throws SQLIntegrityConstraintViolationException {
        if (artist.getName().equals("") || artist.getName().length() > 100)
            throw new SQLIntegrityConstraintViolationException();
        if (artistRepository.findByNameIgnoreCase(artist.getName()).isPresent())
            throw new EntityExistsException();
        Optional<Artist> artistOptional = artistRepository.findById(id);
        Artist updatedArtist = artistOptional.get();
        updatedArtist.setName(artist.getName());
        artistRepository.save(updatedArtist);
        return new RedirectView("/artists/" + artist.getId());
    }

    /**
     * Supprime un artiste et les albums qui lui sont attribués.
     * @param id
     * @return RedirectView. Redirection vers la page d'accueil.
     * @error 404 si l'id n'est pas dans la base de données.
     * @error 405 si l'utilisateur tente de supprimer avec une methode GET.
     */
    @PostMapping(value = "/{id}/delete")
    public RedirectView deleteArtist(
            @PathVariable Integer id) {
        if (artistRepository.findById(id).isEmpty())
            throw new NoSuchElementException();
        artistRepository.deleteById(id);
        return new RedirectView("/");
    }
}
