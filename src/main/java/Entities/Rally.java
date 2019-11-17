package Entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="honor_rally")
public class Rally {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String description;
    @Column
    @Temporal(value = TemporalType.DATE)
    private Date time;
    @Column
    private String author;

    @OneToOne(mappedBy = "rally",fetch = FetchType.EAGER)
    private RallyAlbum album;

    @OneToMany(mappedBy = "rally1",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RallyComments> comments;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(List<RallyComments> comments) {
        this.comments = comments;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAlbum(RallyAlbum album) {
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<RallyComments> getComments() {
        return comments;
    }

    public Date getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public RallyAlbum getAlbum() {
        return album;
    }

    public Rally() {
    }
}
