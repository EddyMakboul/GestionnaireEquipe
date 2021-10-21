import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, Validators } from '@angular/forms';
import { Employee } from '../shared/model/employee.model';
import { Role } from '../shared/model/role.model';
import { EmployeeService } from '../shared/services/employee.service';
import { RoleService } from '../shared/services/role.service';

@Component({
  selector: 'app-new-dev',
  templateUrl: './new-dev.component.html',
  styleUrls: ['./new-dev.component.css']
})
export class NewDevComponent implements OnInit {

  devForm: FormGroup;

  nameControl: FormControl;
  firstNameControl: FormControl;
  roleControl: FormControl;

  employe: Employee = new Employee();
  roles: Role[];
  role: Role;

  constructor(private roleService: RoleService, private employeService: EmployeeService) { }

  ngOnInit(): void {
    this.roleService.getAllProjets().subscribe(roles => {
      this.roles = roles;
      console.log(roles)
    });
    this.createFormControls();
    this.createForm();
  }

  createFormControls(): void {
    this.firstNameControl = new FormControl("", Validators.required);
    this.nameControl = new FormControl("", Validators.required);
    this.roleControl = new FormControl("", Validators.required);
  }
  createForm() {
    this.devForm = new FormGroup({
      name: this.nameControl,
      firstName: this.firstNameControl,
      role: this.roleControl,
    });
  }

  onSubmit() {
    console.log(this.roleControl.value)
    if (this.devForm.valid) {
      console.log("Form Submitted!");
      this.roleService.findById(this.roleControl.value).subscribe(
        role => {
          this.employe.role = role;
          this.employe.nom = this.nameControl.value
          this.employe.prenom = this.firstNameControl.value
          this.employeService.create(this.employe).subscribe(
            response => {
              console.log(response);
            }
          )
        }
      );

    }
  }

}
