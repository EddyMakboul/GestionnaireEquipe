import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../shared/model/employee.model';
import { Projet } from '../shared/model/projet.model';
import { EmployeeService } from '../shared/services/employee.service';
import { ProjetService } from '../shared/services/projet.service';

@Component({
  selector: 'app-new-member',
  templateUrl: './new-member.component.html',
  styleUrls: ['./new-member.component.css']
})
export class NewMemberComponent implements OnInit {

  employes: Employee[] = [];
  projet: Projet;

  memberForm: FormGroup;
  employeControl: FormControl;

  tempEmploye: Number[] = [];

  constructor(private employeService: EmployeeService,
    private projetService: ProjetService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      params => {
        this.projetService.getProjetById(params.id).subscribe(
          projet => {
            this.projet = projet;
            this.employeService.findAll().subscribe(
              employes => {
                if (this.projet.employes) {
                  this.tempEmploye = this.projet.employes.map(m => m.id)
                  this.employes = employes.filter(v => !this.tempEmploye.includes(v.id))
                }
              }
            )
          }
        )
      }
    )
    this.createFormControls();
    this.createForm();
  }

  createFormControls(): void {
    this.employeControl = new FormControl("", Validators.required);
  }
  createForm() {
    this.memberForm = new FormGroup({
      employe: this.employeControl,
    });
  }

  onSubmit() {
    if (this.memberForm.valid) {
      this.employeService.addProjet(this.projet, this.employeControl.value).subscribe(
        response => {
          this.router.navigate(['/project/', this.projet.id])
        }
      )
    }
  }

}
