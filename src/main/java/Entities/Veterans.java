package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_veterans")
public class Veterans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String fio;
    @Column
    private String post;
    @Column
    private String rank;
    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(
            name="honor_ordens_people",
            joinColumns = {@JoinColumn(name="veteran_id")},
            inverseJoinColumns={@JoinColumn(name="orden_id")}
    )
    private List<Ordens> ordens;



    public void setOrdens(List<Ordens> ordens) {
        this.ordens = ordens;
    }

    public Veterans() {
    }


    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getPost() {
        return post;
    }

    public String getRank() {
        return rank;
    }
}
