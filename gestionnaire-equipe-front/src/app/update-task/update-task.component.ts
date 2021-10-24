import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../shared/model/employee.model';
import { Tache } from '../shared/model/tache.model';
import { EmployeeService } from '../shared/services/employee.service';
import { TacheService } from '../shared/services/tache.service';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
export class UpdateTaskComponent implements OnInit {

  tache: Tache;

  updateTaskForm: FormGroup;
  nomControls: FormControl;
  descriptionControls: FormControl;
  employeControl: FormControl;

  constructor(private tacheService: TacheService,
    private employeService: EmployeeService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      params => {
        this.tacheService.getTacheById(params.id).subscribe(
          tache => {
            this.tache = tache;
            this.createFormControls();
            this.createForm();
          }
        )
      }
    );
  }

  createFormControls(): void {
    this.nomControls = new FormControl("", Validators.required);
    this.descriptionControls = new FormControl("", Validators.required);
    this.employeControl = new FormControl("");
    this.nomControls.setValue(this.tache.nom_tache);
    this.descriptionControls.setValue(this.tache.description);
    this.employeControl.setValue(this.tache.employe?.id);
    this.nomControls.updateValueAndValidity();
    this.descriptionControls.updateValueAndValidity();
    this.employeControl.updateValueAndValidity();
  }
  createForm() {
    this.updateTaskForm = new FormGroup({
      name: this.nomControls,
      description: this.descriptionControls,
      employe: this.employeControl,
    });
  }

  onSubmit(): void {

    if (this.updateTaskForm.valid) {
      if (this.employeControl.value) {
        console.log(this.employeControl.value)
        this.employeService.findById(this.employeControl.value).subscribe(
          employe => {
            this.tache.employe = employe;
            this.tache.nom_tache = this.nomControls.value;
            this.tache.description = this.descriptionControls.value;
            this.tacheService.update(this.tache).subscribe(
              response => {
                this.router.navigate(['/project/', this.tache.projet.id]);
              }
            )
          }
        );
      }
      else {
        this.tache.employe = null;
        this.tache.nom_tache = this.nomControls.value;
        this.tache.description = this.descriptionControls.value;
        this.tacheService.update(this.tache).subscribe(
          response => {
            this.router.navigate(['/project/', this.tache.projet.id]);
          }
        )
      }

    }
  }


}
