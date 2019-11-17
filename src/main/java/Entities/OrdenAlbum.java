package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="honor_ordens_album")
public class OrdenAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER)
    private List<OrdenImage> images;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="orden_id")
    private Ordens orden;

    public void setOrden(Ordens orden) {
        this.orden = orden;
    }

//    public Ordens getOrden() {
//        return orden;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImages(List<OrdenImage> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<OrdenImage> getImages() {
        return images;
    }

    public OrdenAlbum() {
    }
}
