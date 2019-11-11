package com.honor.back.honorwebapp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="honor_gallery_albums")
public class GalleryAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    @Temporal(TemporalType.DATE)
    private Date creation_date;

    @OneToMany(mappedBy = "album",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<GalleryImage> images;
    public GalleryAlbum() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setImages(List<GalleryImage> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public List<GalleryImage> getImages() {
        return images;
    }
}
