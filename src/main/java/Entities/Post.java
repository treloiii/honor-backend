package Entities;

import Entities.deprecated.PostComments;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="posts")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@ToString(of = {"id","title"})
@NoArgsConstructor
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Lob
    @Column
    private String description;
    @Column
    private String title_image;
    @Temporal(TemporalType.DATE)
    @Column
    private Date time;
    @Column
    private String title_image_name;
    @Column
    private String author;
    @Column
    private String title_image_mini;
    @Column
    private String description_short;
    @Column
    private String type;
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Comments> comments;

    public Post(int id,String title,String title_image,String title_image_mini) {
        this.id=id;
        this.title=title;
        this.title_image=title_image;
        this.title_image_mini=title_image_mini;
    }
}
