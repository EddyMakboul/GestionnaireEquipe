import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Projet } from '../shared/model/projet.model';
import { ProjetService } from '../shared/services/projet.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  isLogoSwitched: boolean = false;

  projects: Projet[] = [];
  constructor(private projectService: ProjetService, private router: Router) { }

  ngOnInit(): void {
    this.getProjetList();
  }

  goToNewDev(): void {
    this.router.navigate(['/new-dev']);
  }

  goToNewProject(): void {
    this.router.navigate(['/new-project']);
  }

  switchLogo() {
    this.isLogoSwitched = !this.isLogoSwitched;
    this.getProjetList();
  }

  getProjetList() {
    this.projectService.getAllProjets().subscribe(response => {
      this.projects = response;
    });
  }

}
