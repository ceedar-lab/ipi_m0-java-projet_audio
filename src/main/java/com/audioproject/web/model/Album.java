package com.audioproject.web.model;

import javax.persistence.*;

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
}
