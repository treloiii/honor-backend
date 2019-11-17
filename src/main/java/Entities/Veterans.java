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
    private String name;
    @Column
    private String surname;
    @Column
    private String patronomyc;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronomyc(String patronomyc) {
        this.patronomyc = patronomyc;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronomyc() {
        return patronomyc;
    }

    public String getRank() {
        return rank;
    }
}
