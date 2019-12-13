package Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="honor_main_posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String title_image;
    @Temporal(value = TemporalType.DATE)
    @Column
    private Date time;
    @Column
    private String title_image_name;
    @Column
    private String author;


    public Post() {
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTitle_image_name(String title_image_name) {
        this.title_image_name = title_image_name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public String getTitle_image_name() {
        return title_image_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitle_image() {
        return title_image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
    }

    @Override
    public String toString() {
        return "id="+id+",title="+title+",desc="+description+",image="+ title_image;
    }
}
