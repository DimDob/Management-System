import { Component, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EpicsTableComponent } from './epics-table/epics-table.component';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateEpicModal } from './createEpicModal/create-epic-modal.component';
import { FetchEpicModalComponent } from './fetch-epic-modal/fetch-epic-modal.component';
import { UpdateEpicComponent } from './update-epic/update-epic.component';
import { DeleteEpicModalComponent } from './delete-epic-modal/delete-epic-modal.component';
import { PatchEpicModalComponent } from './patch-epic-modal/patch-epic-modal.component';
import { DeleteAllEpicsModalComponent } from './delete-all-epics-modal/delete-all-epics-modal.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'jira-copyUI';

  @ViewChild('name', { static: true }) name: ElementRef | undefined;

  @ViewChild('epicId', { static: true }) epicId: ElementRef | undefined;

  @ViewChild('fetchButton', { static: true }) fetchButton:
    | ElementRef
    | undefined;

  @ViewChild('updateParams', { static: true }) updateParams:
    | ElementRef
    | undefined;

  @ViewChild('id', { static: true }) id: ElementRef | undefined;

  @ViewChild('deleteId', { static: true }) deleteId: ElementRef | undefined;

  @ViewChild('patchId', { static: true }) patchId: ElementRef | undefined;

  @ViewChild('patchParams', { static: true }) patchParams:
    | ElementRef
    | undefined;

  displayedColumns: string[] = ['EpicId', 'name'];
  sort: any;
  data: any;
  length: any;
  pageSize: any;
  disabled: any;
  showFirstLastButtons: any;
  showPageSizeOptions: any;
  pageSizeOptions: any;
  hidePageSize: any;
  pageIndex: any;

  router: Router;
  constructor(
    private http: HttpClient,
    private elementRef: ElementRef,
    private dialogRef: MatDialog
  ) {
    this.epicsTableComponent = new EpicsTableComponent(
      this.http,
      this,
      this.router
    );
  }

  epicsTableComponent: EpicsTableComponent;
  showTable: boolean = false;
  epicsUrl = 'http://localhost:8080/epics';

  get() {
    alert('Fetching all epics!');
    this.epicsTableComponent.get();
    this.showTable = true;
  }

  create(event: Event) {
    event.preventDefault();
    this.createEpicModalDialog();
  }

  fetch(event: Event) {
    event.preventDefault();
    this.createFetchEpicModalDialog();
  }

  update(event: Event) {
    event.preventDefault();
    this.createUpdateEpicModalDialog();
  }

  delete(event: Event) {
    event.preventDefault();
    this.createDeleteEpicModalDialog();
  }

  patch(event: Event) {
    event.preventDefault();
    this.createPatchEpicModalDialog();
  }

  deleteAll(event: Event) {
    event.preventDefault();
    this.createDeleteAllEpicsModalDialog();
  }

  createEpicModalDialog() {
    this.dialogRef.open(CreateEpicModal);
  }

  createFetchEpicModalDialog() {
    this.dialogRef.open(FetchEpicModalComponent);
  }
  createUpdateEpicModalDialog() {
    this.dialogRef.open(UpdateEpicComponent);
  }

  createDeleteEpicModalDialog() {
    this.dialogRef.open(DeleteEpicModalComponent);
  }
  createPatchEpicModalDialog() {
    this.dialogRef.open(PatchEpicModalComponent);
  }
  createDeleteAllEpicsModalDialog() {
    this.dialogRef.open(DeleteAllEpicsModalComponent);
  }
}
