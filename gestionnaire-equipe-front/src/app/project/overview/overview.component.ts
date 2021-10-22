import { Component, Input, OnInit } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  @Input() projet: Projet;

  constructor() { }

  ngOnInit(): void {
    console.log(this.projet)
  }

}
