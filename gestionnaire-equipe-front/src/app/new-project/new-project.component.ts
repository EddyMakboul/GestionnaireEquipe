import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Projet } from '../shared/model/projet.model';
import { EmployeeService } from '../shared/services/employee.service';
import { ProjetService } from '../shared/services/projet.service';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css']
})
export class NewProjectComponent implements OnInit {


  projetForm: FormGroup;
  nameControl: FormControl;
  descrptionControl: FormControl;

  projet: Projet = new Projet();

  constructor(private projetService: ProjetService, private employeService: EmployeeService) { }

  ngOnInit(): void {
    this.createFormControls();
    this.createForm();
  }

  createFormControls(): void {
    this.descrptionControl = new FormControl("", Validators.required);
    this.nameControl = new FormControl("", Validators.required);
  }
  createForm() {
    this.projetForm = new FormGroup({
      name: this.nameControl,
      description: this.descrptionControl,
    });
  }

  onSubmit() {
    if (this.projetForm.valid) {
      console.log("Form Submitted!");
      this.projet.nom_projet = this.nameControl.value;
      this.projet.description = this.descrptionControl.value
      this.projetService.create(this.projet).subscribe(
        response => {
        }
      )
    }
  }
}

