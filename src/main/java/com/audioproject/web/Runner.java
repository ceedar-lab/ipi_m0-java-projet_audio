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

    @Override
    public void run(String... args) {
    }
}
