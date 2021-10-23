import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Projet } from 'src/app/shared/model/projet.model';
import { Tache } from 'src/app/shared/model/tache.model';
import { TacheService } from 'src/app/shared/services/tache.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit, OnChanges {

  @Input() projet: Projet;
  @Input() taches: Tache[];

  @Output() projetEventEmiter = new EventEmitter<void>();

  freeList: Tache[];
  inProgressList: Tache[];
  doneList: Tache[];
  bigList: Tache[];

  constructor(private tacheService: TacheService) { }


  ngOnInit(): void {
    this.parseTaches(this.taches);
  }

  ngOnChanges(changes: SimpleChanges): void {
    let change = changes['taches'];
    if (change != undefined)
      this.parseTaches(change.currentValue)
  }

  parseTaches(taches: Tache[]): void {
    this.freeList = this.taches.filter(tache => tache.employe == null && !tache.finished)
    this.inProgressList = this.taches.filter(tache => tache.employe && !tache.finished)
    this.doneList = this.taches.filter(tache => tache.finished)
  }

  clicked(tache_id: number, finished: boolean) {
    this.tacheService.finishedtache(tache_id, finished).subscribe(
      response => {
        this.projetEventEmiter.emit();
      }
    )

  }

}
