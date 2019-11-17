package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="honor_actions_type")
public class ActionsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String Name;
    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER)
    private List<Actions> actions;

    public ActionsType() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }
}
