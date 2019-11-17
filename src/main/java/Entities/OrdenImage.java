package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_ordens_image")
public class OrdenImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String server_path;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private OrdenAlbum album;


    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getServer_path() {
        return server_path;
    }

    public OrdenImage() {
    }
}
