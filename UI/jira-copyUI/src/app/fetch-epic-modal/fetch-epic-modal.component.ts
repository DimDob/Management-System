import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
} from '@angular/material/dialog';
import { Router } from '@angular/router';

export interface DialogData {
  epicId: string;
}

@Component({
  selector: 'app-fetch-epic-modal',
  templateUrl: './fetch-epic-modal.component.html',
  styleUrls: ['./fetch-epic-modal.component.css'],
})
export class FetchEpicModalComponent {
  epicId: string;
  epicsUrl = 'http://localhost:8080/epics';

  constructor(
    public dialog: MatDialog,
    private http: HttpClient,
    private router: Router
  ) {}

  fetchEpicEntity(): void {
    const dialogRef = this.dialog.open(FetchEpicModalComponent, {
      data: { epicId: this.epicId },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.epicId = result.epicId;
    });
    this.fetch(this.epicId);
  }

  fetch(epicId: string) {
    this.fetchEpicById(epicId);
  }

  private fetchEpicById(epicId: string) {
    this.http.get(`${this.epicsUrl}/${epicId}`).subscribe(
      (resp: HttpResponse<any>) => {
        const entity = resp;
        const alertMsg = `Epic ID: ${entity['epicId']}\nName: ${entity['name']}`;
        alert('Fetched epic:\n ' + alertMsg);
        window.location.reload();
      },
      (error) => {
        alert('Epic ' + epicId + ' has not been found in the database.');
      }
    );
  }
}
