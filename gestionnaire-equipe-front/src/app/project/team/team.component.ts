import { Component, Input, OnInit } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';
import { Output, EventEmitter } from '@angular/core';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { Employee } from 'src/app/shared/model/employee.model';
import { EmployeeService } from 'src/app/shared/services/employee.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  @Output() projetEventEmiter = new EventEmitter<void>();

  @Input() projet: Projet;
  constructor(private projetService: ProjetService, private employeService: EmployeeService) { }

  ngOnInit(): void {
  }

  removeFromTeam(employe: Employee) {
    this.employeService.removeProjet(this.projet, employe.id).subscribe(
      (data) => {
        this.projetEventEmiter.emit();

      }
    )
  }



}
function data(data: any, arg1: (Employee: any) => void, arg2: void) {
  throw new Error('Function not implemented.');
}

