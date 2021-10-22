import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { NewDevComponent } from './new-dev/new-dev.component';
import { ProjectComponent } from './project/project.component';
import { OverviewComponent } from './project/overview/overview.component';
import { TeamComponent } from './project/team/team.component';
import { TaskComponent } from './project/task/task.component';
import { DevsOverviewComponent } from './devs-overview/devs-overview.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon'

@NgModule({
  declarations: [
    AppComponent,
    SideBarComponent,
    NewProjectComponent,
    NewDevComponent,
    ProjectComponent,
    OverviewComponent,
    TeamComponent,
    TaskComponent,
    DevsOverviewComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatSidenavModule,
    FlexLayoutModule,
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
