import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Employee } from '../shared/model/employee.model';
import { Projet } from '../shared/model/projet.model';
import { Tache } from '../shared/model/tache.model';
import { ProjetService } from '../shared/services/projet.service';
import { TacheService } from '../shared/services/tache.service';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  taskForm: FormGroup;
  nameControl: FormControl;
  descrptionControl: FormControl;
  employeControl: FormControl


  projet: Projet = new Projet();
  task: Tache = new Tache();
  constructor(private projetService: ProjetService,
    private taskService: TacheService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.task.employe = new Employee();
    this.activatedRoute.params.subscribe(params => {
      this.projetService.getProjetById(params.id).subscribe(projet => {
        this.projet = projet;
        console.log(this.projet);
      })
    });
    this.createFormControls();
    this.createForm();
  }

  createFormControls(): void {
    this.descrptionControl = new FormControl("", Validators.required);
    this.nameControl = new FormControl("", Validators.required);
    this.employeControl = new FormControl("", Validators.required);
  }
  createForm() {
    this.taskForm = new FormGroup({
      name: this.nameControl,
      description: this.descrptionControl,
      employe: this.employeControl,
    });
  }

  onSubmit() {
    if (this.taskForm.valid) {
      this.task.employe = this.projet.employes?.find(employe => employe.id = this.employeControl.value);
      this.task.nom_tache = this.nameControl.value;
      this.task.description = this.descrptionControl.value;
      this.task.projet = this.projet;
      this.task.finished = false;
      console.log(this.task)
        ; this.taskService.create(this.task).subscribe(
          response => {
          }
        )
    }
  }

}
