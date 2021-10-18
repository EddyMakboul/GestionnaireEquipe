package gestionnaire.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="T_Tache")
public class Tache implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name="nom_tache",nullable = false)
    private String nom_tache;

    @Basic
    @Column(name="decription",nullable = false)
    private String decription;

    @Basic
    @Column(name="finished",nullable = false)
    private boolean finished;

    @ManyToOne
    @JoinColumn(name="employe")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name="projet")
    private Projet projet;

    public Tache() {
        super();
    }

    public Tache(String nom_tache, String decription, Employe employe, Projet projet) {
        this.nom_tache = nom_tache;
        this.decription = decription;
        this.finished = false;
        this.employe = employe;
        this.projet = projet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom_tache() {
        return nom_tache;
    }

    public void setNom_tache(String nom_tache) {
        this.nom_tache = nom_tache;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
