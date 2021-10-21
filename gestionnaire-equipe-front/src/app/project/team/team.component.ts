import { Component, Input, OnInit } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';
import { Output, EventEmitter } from '@angular/core';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { Employee } from 'src/app/shared/model/employee.model';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  @Output() projetEventEmiter = new EventEmitter<Projet>();

  @Input() projet: Projet;
  constructor(private projetService: ProjetService) { }

  ngOnInit(): void {
    console.log(this.projet)
  }

  removeFromTeam(employe: Employee) {
    this.projetService.removeEmployeeToProjet(employe, this.projet.id).subscribe(
      projet => {
        this.projetEventEmiter.emit()
      }
    )
  }



}
