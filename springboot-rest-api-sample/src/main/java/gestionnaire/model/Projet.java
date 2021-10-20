package gestionnaire.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="T_Projet")
public class Projet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name="nom_projet",nullable = false)
    private String nom_projet;

    @Basic
    @Column(name="description",nullable = false)
    private String description;

    @OneToOne
    private Employe chef_projet;

    @ManyToMany
    @OrderBy("nom ASC")
    private List<Employe> employes;

    @OneToMany(mappedBy="projet")
    @OrderBy("nom_tache ASC")
    private List<Tache> taches;

    public Projet() {
        super();
    }

    public Projet(String nom_projet, String description, Employe chef_projet) {
        this.nom_projet = nom_projet;
        this.description = description;
        this.chef_projet = chef_projet;
        this.employes = new ArrayList<>();
        this.taches = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom_projet() {
        return nom_projet;
    }

    public void setNom_projet(String nom_projet) {
        this.nom_projet = nom_projet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employe getChef_projet() {
        return chef_projet;
    }

    public void setChef_projet(Employe chef_projet) {
        this.chef_projet = chef_projet;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public void addEmploye(Employe employe) {this.employes.add(employe);}
}
