import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DevsOverviewComponent } from './devs-overview/devs-overview.component';
import { NewDevComponent } from './new-dev/new-dev.component';
import { NewMemberComponent } from './new-member/new-member.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { NewTaskComponent } from './new-task/new-task.component';
import { ProjectComponent } from './project/project.component';


const routes: Routes = [
  { path: '', redirectTo: 'devs-overview', pathMatch: 'full' },
  { path: 'devs-overview', component: DevsOverviewComponent },
  { path: 'new-dev', component: NewDevComponent },
  { path: 'new-project', component: NewProjectComponent },
  { path: 'new-member/:id', component: NewMemberComponent },
  { path: 'project/:id', component: ProjectComponent },
  { path: 'new-task/:id', component: NewTaskComponent },
  { path: '**', redirectTo: 'devs-overview', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
