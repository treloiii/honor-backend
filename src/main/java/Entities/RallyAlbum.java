package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_rally_albums")
public class RallyAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rally_id")
    private Rally rally;

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER)
    private List<RallyImage> images;

    public RallyAlbum() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public Rally getRally() {
//        return rally;
//    }

    public List<RallyImage> getImages() {
        return images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRally(Rally rally) {
        this.rally = rally;
    }

    public void setImages(List<RallyImage> images) {
        this.images = images;
    }
}
