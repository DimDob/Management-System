import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatPaginatorModule } from '@angular/material/paginator';
import { EpicsTableComponent } from './epics-table/epics-table.component';
import { Epic } from './epics-table/epic';
import { FeaturesComponent } from './features/features.component';

const routes: Routes = [
  { path: 'epics', component: EpicsTableComponent},
  { path: "epics/:epicId/features", component: FeaturesComponent}

];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), MatPaginatorModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [EpicsTableComponent, FeaturesComponent]
