package gestionnaire.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="T_Employe")
public class Employe implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name="nom",nullable = false)
    private String nom;

    @Basic
    @Column(name="prenom",nullable = false)
    private String prenom;

    @Basic
    @Column(name="login",nullable = false)
    private String login;

    @Basic
    @Column(name="mdp",nullable = false)
    private String mdp;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    @OneToMany(mappedBy="employe")
    @OrderBy("nom_tache ASC")
    private List<Tache> taches;

    @ManyToMany
    @OrderBy("nom_competence ASC")
    private List<Competence> competences;

    @ManyToMany
    @OrderBy("nom_projet ASC")
    private List<Projet> projets;

    public Employe() {
        super();
    }

    public Employe(String nom, String prenom, String login, String mdp, Role role) {
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
        this.taches = new ArrayList<>();
        this.competences = new ArrayList<>();
        this.projets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
}
