import { Employee } from "./employee.model";
import { Tache } from "./tache.model";

export class Projet{
    constructor(
        public id:number,
        public nom_projet:string,
        public chef_projet:Employee,
        public description?:string,
        public employes?:Employee[],
        public taches?: Tache[],
    )
     {

     }
}