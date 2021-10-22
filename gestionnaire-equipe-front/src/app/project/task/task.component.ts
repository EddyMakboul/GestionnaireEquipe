import { Component, Input, OnInit } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';
import { Tache } from 'src/app/shared/model/tache.model';
import { TacheService } from 'src/app/shared/services/tache.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  @Input() projet: Projet;

  freeList: Tache[];
  inProgressList: Tache[];
  doneList: Tache[];
  bigList: Tache[];

  constructor(private tacheService: TacheService) { }

  ngOnInit(): void {
    this.tacheService.getAllTacheByProjetId(this.projet.id).subscribe(
      data => {
        this.freeList = data.filter(tache => tache.employee == null && !tache.finished)
        this.inProgressList = data.filter(tache => tache.employee && !tache.finished)
        this.doneList = data.filter(tache => tache.finished)
      }
    );
  }

}
