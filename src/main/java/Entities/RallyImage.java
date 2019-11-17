package Entities;

import javax.persistence.*;

@Entity
@Table(name="honor_rally_images")
public class RallyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private RallyAlbum album;

    @Column
    private String url;

    @Column
    private String server_path;

    public RallyImage() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbum(RallyAlbum album) {
        this.album = album;
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

//    public RallyAlbum getAlbum() {
//        return album;
//    }

    public String getUrl() {
        return url;
    }

    public String getServer_path() {
        return server_path;
    }
}
