package Entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "honor_news")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News extends Redactable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Lob
    @Column
    private String description;
    @Column
    @Temporal(TemporalType.DATE)
    private Date time;
    @Column
    private String author;
    @Column
    private String title_image;
    @Column
    private String title_image_name;

    public News(int id, String title, String title_image) {
        this.id = id;
        this.title = title;
        this.title_image = title_image;
    }

    public News() {
//        this.id=id;
//        this.title=title;
//        this.title_image=title_image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle_image_name(String title_image_name) {
        this.title_image_name = title_image_name;
    }

    public String getTitle_image_name() {
        return title_image_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
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

    public Date getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle_image() {
        return title_image;
    }

}
