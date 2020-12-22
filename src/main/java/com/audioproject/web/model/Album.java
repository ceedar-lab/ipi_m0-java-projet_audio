package com.audioproject.web.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Album {

    /** Attributes **/
    @Id
    @Column(name = "AlbumId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private Artist artist;

    /** Getters / Setters **/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /** Methods **/
    @Override
    public String toString() {
        return "Album{id=" +id+ ", title=" +title+ ", artist=" +artist.getId()+ "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id) &&
                Objects.equals(title, album.title) &&
                Objects.equals(artist, album.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist);
    }
}
