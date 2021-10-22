import { Employee } from "./employee.model";
import { Projet } from "./projet.model";

export class Tache {

    public id: number;
    public nom_tache: string;
    public description: string;
    public finished: boolean;
    public employee?: Employee;
    public projet: Projet;
}