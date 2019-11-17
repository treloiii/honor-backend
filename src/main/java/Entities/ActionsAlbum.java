package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_actions_albums")
public class ActionsAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rally_id")
    private Actions actions;

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER)
    private List<ActionsImage> images;

    public ActionsAlbum() {
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

    public List<ActionsImage> getImages() {
        return images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public void setImages(List<ActionsImage> images) {
        this.images = images;
    }
}
