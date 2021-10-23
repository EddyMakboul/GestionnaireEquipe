import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Projet } from '../shared/model/projet.model';
import { Tache } from '../shared/model/tache.model';
import { ProjetService } from '../shared/services/projet.service';
import { TacheService } from '../shared/services/tache.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {


  project: Projet;
  id: number;
  view: number = 1;
  taches: Tache[];

  constructor(private activatedRoute: ActivatedRoute,
    private projetService: ProjetService,
    private router: Router,
    private tacheService: TacheService) { }

  ngOnInit(): void {
    this.getProjet();
  }

  getProjet(): void {
    this.activatedRoute.params.subscribe(params => {
      this.id = params.id;
      this.projetService.getProjetById(params.id).subscribe(projet => {
        this.project = projet;
        this.tacheService.getAllTacheByProjetId(params.id).subscribe(
          data => {
            this.taches = data;
          }
        );
      })
    });
  }

  updateProjet() {
    this.getProjet();
  }

  goToOverview() {
    this.view = 1;
  }
  goToTask() {
    this.view = 2;
  }

  goToTeam() {
    this.view = 3;
  }


}
