import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../shared/model/employee.model';
import { Projet } from '../shared/model/projet.model';
import { EmployeeService } from '../shared/services/employee.service';
import { ProjetService } from '../shared/services/projet.service';

@Component({
  selector: 'app-update-leader',
  templateUrl: './update-leader.component.html',
  styleUrls: ['./update-leader.component.css']
})
export class UpdateLeaderComponent implements OnInit {

  projet: Projet;
  leaders: Employee[];

  leaderForm: FormGroup;
  leaderControls: FormControl;

  constructor(private activatedRoute: ActivatedRoute,
    private projetService: ProjetService,
    private employeService: EmployeeService,
    private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      params => {
        this.projetService.getProjetById(params.id).subscribe(
          projet => {
            this.projet = projet;
          }
        )
      }
    );
    this.employeService.getAllChef().subscribe(
      leaders => {
        this.leaders = leaders;
      }
    )
    this.createFormControls();
    this.createForm();

  }

  createFormControls(): void {
    this.leaderControls = new FormControl("", Validators.required);

  }
  createForm() {
    this.leaderForm = new FormGroup({
      chef: this.leaderControls,
    });
  }


  onSubmit(): void {
    if (this.leaderForm.valid) {
      this.employeService.findById(this.leaderControls.value).subscribe(
        employe => {
          this.projet.chef_projet = employe;
          this.projetService.update(this.projet).subscribe(
            projet => {
              this.router.navigate(['/project', this.projet.id])
            }
          )
        }
      )
    }

  }
}
