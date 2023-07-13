import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EpicsTableComponent } from '../epics-table/epics-table.component';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-all-epics-modal',
  templateUrl: './delete-all-epics-modal.component.html',
  styleUrls: ['./delete-all-epics-modal.component.css'],
})
export class DeleteAllEpicsModalComponent {
  epicsUrl = 'http://localhost:8080/epics';

  epicsTableComponent: EpicsTableComponent;

  constructor(
    private dialog: MatDialog,
    private http: HttpClient,
    private appComponent: AppComponent,
    private router: Router
  ) {
    this.epicsTableComponent = new EpicsTableComponent(
      this.http,
      this.appComponent,
      this.router
    );
  }

  delete(): void {
    const dialogRef = this.dialog.open(DeleteAllEpicsModalComponent, {
      data: { params: 'Are you sure you want to delete all?' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });

    this.deleteAllEpics();
  }

  deleteAllEpics() {
    const epics = this.epicsTableComponent.dataSource.data;

    epics.forEach((epic) => {
      const id = epic.epicId;
      this.http
        .delete(`${this.epicsUrl}/${id}`)
        .subscribe((resp: HttpResponse<any>) => {});
    });
    alert('All epics are deleted');
    this.router.navigate(['/']);
    window.location.reload();
  }
}
