package Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="honor_actions")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Actions extends Redactable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Lob
    @Column
    private String description;
    @Column
    @Temporal(value = TemporalType.DATE)
    private Date time;
    @Column
    private String author;
    @Column
    private String title_image;
    @Column
    private String title_image_name;
    @Column(name="crop_coord")
    private String coords;


    @OneToMany(mappedBy = "actions1",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ActionsComments> comments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type")
    private ActionsType type;

    public Actions(int id, String title, String title_image,String coords) {
        this.id = id;
        this.title = title;
        this.title_image = title_image;
        this.coords=coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getCoords() {
        return coords;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
    }

    public void setTitle_image_name(String title_image_name) {
        this.title_image_name = title_image_name;
    }

    public String getTitle_image() {
        return title_image;
    }

    public String getTitle_image_name() {
        return title_image_name;
    }

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


    public Actions() {
    }
}
