import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Employee } from '../model/employee.model';
import { Observable, throwError } from 'rxjs';
import { Competence } from '../model/competence.model';
import { Projet } from '../model/projet.model';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private ressourceUrl = 'http://localhost:8080/api/employes'

  constructor(private http: HttpClient) { }


  findAll(): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(this.ressourceUrl);
  }

  findById(id_employe: Number): Observable<Employee> {
    return this.http.get<Employee>(this.ressourceUrl + '/' + id_employe);
  }
  create(employe: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.ressourceUrl, employe);
  }

  update(employe: Employee): Observable<Employee> {
    return this.http.put<Employee>(this.ressourceUrl, employe);
  }

  delete(id_employe: Number): Observable<any> {
    return this.http.delete(this.ressourceUrl + '/' + id_employe);
  }

  addCompetence(competence: Competence, id_employe: Number) {
    return this.http.post(this.ressourceUrl + '/addcompetence' + id_employe, competence);
  }

  removeCompetence(competence: Competence, id_employe: Number) {
    return this.http.post(this.ressourceUrl + '/removecompetence' + id_employe, competence);
  }
  removeProjet(projet: Projet, id_employe: Number) {
    return this.http.put(this.ressourceUrl + '/removeprojet/' + id_employe, projet);
  }

  addProjet(projet: Projet, id_employe: Number) {
    return this.http.put(this.ressourceUrl + '/addprojet/' + id_employe, projet);
  }

}
