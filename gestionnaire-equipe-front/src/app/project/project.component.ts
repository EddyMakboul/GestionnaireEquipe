import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Projet } from '../shared/model/projet.model';
import { ProjetService } from '../shared/services/projet.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {


  project: Projet;
  id: number;
  view: number = 1;
  constructor(private activatedRoute: ActivatedRoute,
    private projetService: ProjetService,
    private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.id = params.id;
      this.projetService.getProjetById(params.id).subscribe(projet => {
        this.project = projet;
      })
    });
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

  updateProjet() {
    this.projetService.getProjetById(this.id).subscribe(projet => {
      this.project = projet;
    })
  }

}
