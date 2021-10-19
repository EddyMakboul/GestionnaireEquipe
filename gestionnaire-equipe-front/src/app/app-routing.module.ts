import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DevsOverviewComponent } from './devs-overview/devs-overview.component';
import { NewDevComponent } from './new-dev/new-dev.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { OverviewComponent } from './project/overview/overview.component';
import { ProjectComponent } from './project/project.component';
import { TaskComponent } from './project/task/task.component';
import { TeamComponent } from './project/team/team.component';


const routes: Routes = [
  { path: '', redirectTo: 'devs-overview', pathMatch: 'full' },
  { path: 'devs-overview', component: DevsOverviewComponent },
  { path: 'new-dev', component: NewDevComponent },
  { path: 'new-project', component: NewProjectComponent },
  {
    path: 'project/:id',
    component: ProjectComponent,
    children: [
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: OverviewComponent },
      { path: 'team', component: TeamComponent },
      { path: 'task', component: TaskComponent },
      { path: '**', redirectTo: 'overview', pathMatch: 'full' },
    ]
  },
  { path: '**', redirectTo: 'devs-overview', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
