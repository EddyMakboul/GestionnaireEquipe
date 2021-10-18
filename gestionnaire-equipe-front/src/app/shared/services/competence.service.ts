import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Competence } from '../model/competence.model';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {

  private ressourceUrl = 'localhost://8080/api/competences'

  constructor(private http: HttpClient) { }

  getAllCompetencesNotInEmployee(id_employee: Number): Observable<Array<Competence>> {
    return this.http.get<Array<Competence>>(this.ressourceUrl + '/' + id_employee);
  }

}
