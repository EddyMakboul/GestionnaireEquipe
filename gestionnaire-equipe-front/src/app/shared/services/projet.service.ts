import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../model/employee.model';
import { Projet } from '../model/projet.model';

@Injectable({
  providedIn: 'root'
})
export class ProjetService {

  private ressourceUrl = 'localhost://8080/api/projets'

  constructor(private http: HttpClient) { }

  getAllProjetById(id_projet: number): Observable<Projet> {
    return this.http.get<Projet>(this.ressourceUrl + '/' + id_projet);
  }

  getAllProjets(): Observable<Projet[]> {
    return this.http.get<Projet[]>(this.ressourceUrl);
  }

  create(projet: Projet): Observable<Projet> {
    return this.http.post<Projet>(this.ressourceUrl, projet);
  }

  update(projet: Projet): Observable<Projet> {
    return this.http.put<Projet>(this.ressourceUrl, projet);
  }

  delete(id_projet: number): Observable<any> {
    return this.http.delete(this.ressourceUrl + '/' + id_projet);
  }

  addEmployeeToProjet(employees: Employee[], id_projet: number): Observable<any> {
    return this.http.post(this.ressourceUrl + '/' + id_projet, employees);
  }

  removeEmployeeToProjet(employee: Employee, id_projet: number): Observable<any> {
    return this.http.post(this.ressourceUrl + '/' + id_projet, employee);
  }

  updateChefDeProjet(employee: Employee, id_projet: number): Observable<any> {
    return this.http.post(this.ressourceUrl + '/' + id_projet, employee);
  }

}
