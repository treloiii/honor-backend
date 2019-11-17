package Entities;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_ordens")
public class Ordens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String small_description;
    @ManyToMany(mappedBy = "ordens",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Veterans> veterans;
    @OneToOne(mappedBy = "orden")
    private OrdenAlbum album;


    public void setAlbum(OrdenAlbum album) {
        this.album = album;
    }

    public OrdenAlbum getAlbum() {
        return album;
    }

    public void setSmall_description(String small_description) {
        this.small_description = small_description;
    }

    public String getSmall_description() {
        return small_description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVeterans(List<Veterans> veterans) {
        this.veterans = veterans;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Veterans> getVeterans() {
        return veterans;
    }

    public Ordens() {
    }
}
