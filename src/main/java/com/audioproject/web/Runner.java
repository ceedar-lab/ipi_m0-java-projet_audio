package com.audioproject.web;

import com.audioproject.web.model.Album;
import com.audioproject.web.model.Artist;
import com.audioproject.web.repository.AlbumRepository;
import com.audioproject.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    AlbumRepository albumRepository;

    public static void print(Object t) {
        System.out.println(t);
    }

    @Override
    public void run(String... args) throws Exception {
        /*List<Artist> artist = artistRepository.findAllByNameContaining("aerosmith");
        for (Artist a : artist) {
            print(a);
        }*/

        /*PageRequest pageRequest = PageRequest.of(0, 2, ASC, "id");
        print(artistRepository.findAllByNameContaining("aero", pageRequest).getContent());*/

        /*PageRequest p = PageRequest.of(0, 10, "ASC", "name");*/

        /*Optional<Artist> artist = artistRepository.findById(2);
        if (artist.isPresent()) {
            Artist a = artist.get();
            List<Album> albums = a.getAlbums();
            for (Album album : albums) {
                print(album.getTitle());
            }
        }*/

        /*Optional<Artist> artist = artistRepository.findById(1);
        List<Album> albums = Artist artist.getAlbums();
        for (Album a : albums) {
            System.out.println(a.getTitle());
        }*/

        /*Artist artist = artistRepository.getOne(1);
        System.out.println(artist);*/

        /*Optional<Album> album = albumRepository.findById(1);
        System.out.println(album);*/

        /*List<Artist> artists = artistRepository.findAll();
        for (Artist artist : artists) {
            System.out.println(artist);
        }*/

        /*List<Album> albums = albumRepository.findAll();
        for (Album album : albums) {
            System.out.println(album.toString());
        }*/
    }
}
