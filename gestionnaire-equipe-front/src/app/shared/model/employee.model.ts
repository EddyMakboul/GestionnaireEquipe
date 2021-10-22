import { Competence } from "./competence.model";
import { Projet } from "./projet.model";
import { Role } from "./role.model";
import { Tache } from "./tache.model";

export class Employee {
    public id: number;
    public nom: string;
    public prenom: string;
    public role?: Role;
    public taches?: Tache[];
    public competences?: Competence[];
    public projet?: Projet;

};