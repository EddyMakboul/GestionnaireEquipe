import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tache } from '../model/tache.model';

@Injectable({
  providedIn: 'root'
})
export class TacheService {

  private ressourceUrl = 'localhost://8080/api/taches'

  constructor(private http: HttpClient) { }

  getAllTacheActiveByProjetId(id_projet: number): Observable<Tache[]> {
    return this.http.get<Tache[]>(this.ressourceUrl + '/' + id_projet);
  }

  create(tache: Tache): Observable<Tache> {
    return this.http.post<Tache>(this.ressourceUrl, tache);
  }

  update(tache: Tache): Observable<Tache> {
    return this.http.put<Tache>(this.ressourceUrl, tache);
  }

  delete(id_tache: number): Observable<any> {
    return this.http.delete(this.ressourceUrl + '/' + id_tache);
  }

  finishedtache(id_tache: number): Observable<any> {
    return this.http.post(this.ressourceUrl + '/finished', id_tache);
  }

}
