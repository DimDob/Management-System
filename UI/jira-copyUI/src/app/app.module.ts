import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { AppComponent } from './app.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatExpansionModule} from '@angular/material/expansion';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule } from '@angular/material/dialog';
import { CreateEpicModal } from './createEpicModal/create-epic-modal.component';
import { FormsModule } from '@angular/forms';
import { FetchEpicModalComponent } from './fetch-epic-modal/fetch-epic-modal.component';
import { UpdateEpicComponent } from './update-epic/update-epic.component';
import { DeleteEpicModalComponent } from './delete-epic-modal/delete-epic-modal.component';
import { PatchEpicModalComponent } from './patch-epic-modal/patch-epic-modal.component';
import { DeleteAllEpicsModalComponent } from './delete-all-epics-modal/delete-all-epics-modal.component';


@NgModule({
  declarations: [AppComponent, routingComponents, CreateEpicModal, FetchEpicModalComponent, UpdateEpicComponent, DeleteEpicModalComponent, PatchEpicModalComponent, DeleteAllEpicsModalComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatSortModule,
    MatFormFieldModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatExpansionModule,
    MatInputModule,
    MatDialogModule,
    FormsModule
  ],
  providers: [    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}, AppComponent  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
