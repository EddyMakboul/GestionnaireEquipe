import { Component, Input, OnInit } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  @Input() projet: Projet;

  constructor() { }

  ngOnInit(): void {
  }

}
