package gestionnaire.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="T_Competence")
public class Competence implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name="nom_competence",nullable = false)
    private String nom_competence;

    @Basic
    @Column(name="niveau",nullable = false)
    private String niveau;

    public Competence() {
        super();
    }

    public Competence(String nom, String niveau) {
        this.nom_competence = nom;
        this.niveau = niveau;
    }

    public String getNomCompetence() {
        return nom_competence;
    }

    public void setNomCompetence(String nom) {
        this.nom_competence = nom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
