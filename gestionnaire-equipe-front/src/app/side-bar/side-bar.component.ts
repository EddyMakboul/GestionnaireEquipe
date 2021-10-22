import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  isLogoSwitched : boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  switchLogo() {
      this.isLogoSwitched = !this.isLogoSwitched;
  }

}
