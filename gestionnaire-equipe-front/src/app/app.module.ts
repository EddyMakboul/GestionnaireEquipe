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
import { ProjetService } from './shared/services/projet.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RoleService } from './shared/services/role.service';
import { CompetenceService } from './shared/services/competence.service';
import { EmployeeService } from './shared/services/employee.service';
import { TacheService } from './shared/services/tache.service';
import { NewTaskComponent } from './new-task/new-task.component';
import { NewMemberComponent } from './new-member/new-member.component';
import { UpdateLeaderComponent } from './update-leader/update-leader.component';

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
    NewTaskComponent,
    NewMemberComponent,
    UpdateLeaderComponent,
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
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [ProjetService, EmployeeService, RoleService, CompetenceService, TacheService],
  bootstrap: [AppComponent]
})
export class AppModule { }
