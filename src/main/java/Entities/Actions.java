package Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="honor_actions")
public class Actions {

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

    @OneToOne(mappedBy = "actions",fetch = FetchType.LAZY)
    private ActionsAlbum album;

    @OneToMany(mappedBy = "actions1",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ActionsComments> comments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type")
    private ActionsType type;

    public ActionsType getType() {
        return type;
    }

    public void setType(ActionsType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(List<ActionsComments> comments) {
        this.comments = comments;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAlbum(ActionsAlbum album) {
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

    public List<ActionsComments> getComments() {
        return comments;
    }

    public Date getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ActionsAlbum getAlbum() {
        return album;
    }

    public Actions() {
    }
}
