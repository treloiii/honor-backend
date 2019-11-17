package Entities;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_ordens")
public class Ordens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToMany(mappedBy = "ordens",fetch = FetchType.EAGER)
    private List<Veterans> veterans;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVeterans(List<Veterans> veterans) {
        this.veterans = veterans;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Veterans> getVeterans() {
        return veterans;
    }

    public Ordens() {
    }
}
