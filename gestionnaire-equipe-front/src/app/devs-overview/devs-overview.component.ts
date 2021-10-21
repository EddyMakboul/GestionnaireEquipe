import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Employee } from '../shared/model/employee.model';
import { EmployeeService } from '../shared/services/employee.service';

@Component({
  selector: 'app-devs-overview',
  templateUrl: './devs-overview.component.html',
  styleUrls: ['./devs-overview.component.css']
})
export class DevsOverviewComponent implements OnInit {

  employes: Employee[];

  constructor(private employeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeService.findAll().subscribe(
      employes => {
        this.employes = employes;
      }
    )
  }

  delete(id_employe: number): void {
    this.employeService.delete(id_employe).subscribe(
      response => {
        console.log(response);
      },
      errors => {
        console.log(errors)
      }
    )
  }

}
