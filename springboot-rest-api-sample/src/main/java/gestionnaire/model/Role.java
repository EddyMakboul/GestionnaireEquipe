package gestionnaire.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * Correspond à un role pour un employé
 */

@Entity
@Table(name ="T_Role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name="nom_role",nullable = false, unique = true)
    private String nom_role;

    public Role() {
        super();
    }

    public Role(String nom_role) {
        this.nom_role = nom_role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom_role() {
        return nom_role;
    }

    public void setNom_role(String nom_role) {
        this.nom_role = nom_role;
    }
}
