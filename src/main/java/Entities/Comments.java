package Entities;

import Entities.deprecated.News;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nickname;
    @Column
    private String description;
    @Column
    @Temporal(value = TemporalType.DATE)
    private Date time;
    @Column
    private boolean active;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
