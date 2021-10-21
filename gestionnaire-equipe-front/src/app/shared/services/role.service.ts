import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from '../model/role.model';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private ressourceUrl = ' http://localhost:8080/api/roles'

  constructor(private http: HttpClient) { }

  getAllProjets(): Observable<Role[]> {
    return this.http.get<Role[]>(this.ressourceUrl);
  }
  findById(id_role: number): Observable<Role> {
    return this.http.get<Role>(this.ressourceUrl + '/' + id_role);
  }

}
