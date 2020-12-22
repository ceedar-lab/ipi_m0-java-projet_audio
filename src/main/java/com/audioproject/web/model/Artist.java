package com.audioproject.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Artist {

    /** Attributes **/
    @Id
    @Column(name = "ArtistId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnoreProperties("artist")
    private List<Album> albums;

    /** Getters / Setters **/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    /** Methods **/
    @Override
    public String toString() {
        return "Artist{id=" +id+ ", name=" +name+ ", albums=" +albums+ "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) &&
                Objects.equals(name, artist.name) &&
                Objects.equals(albums, artist.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, albums);
    }
}
