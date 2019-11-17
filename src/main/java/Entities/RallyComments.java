package Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="honor_rally_comments")
public class RallyComments {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rally_id")
    private Rally rally1;
    public RallyComments() {
    }


    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDescription() {
        return description;
    }

    public Date getTime() {
        return time;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setRally(Rally rally1) {
        this.rally1 = rally1;
    }
}
