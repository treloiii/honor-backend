package com.honor.back.honorwebapp;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="honor_gallery_comments")
public class GalleryComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nickname;
    @Column
    private BigInteger time;
    @Column
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photo_id")
    private GalleryImage image;

    public GalleryComments() {
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public BigInteger getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

//    public GalleryImage getImage() {
//        return image;
//    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setImage(GalleryImage image) {
        this.image = image;
    }

}
