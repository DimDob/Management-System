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
  updateParams: string;
}
@Component({
  selector: 'app-update-epic',
  templateUrl: './update-epic.component.html',
  styleUrls: ['./update-epic.component.css'],
})
export class UpdateEpicComponent {
  epicId: string;
  updateParams: string;
  epicsUrl = 'http://localhost:8080/epics';

  constructor(
    public dialog: MatDialog,
    private http: HttpClient,
    private router: Router
  ) {}

  updateEpicEntity(): void {
    const dialogRef = this.dialog.open(UpdateEpicComponent, {
      data: { epicId: this.epicId },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.epicId = result.epicId;
    });
    this.update(this.epicId, this.updateParams);
  }

  update(epicId: string, updateParams: string) {
    this.updateEpic(epicId, updateParams);
  }
  private updateEpic(epicId: string, updateParams: string) {
    if (
      epicId === undefined ||
      updateParams === undefined ||
      epicId.length != 36 ||
      updateParams.length == 0
    ) {
      alert('Please enter valid input');
    } else {
      const newName = this.updateParams;

      const options = { headers: { 'Content-Type': 'application/json' } };

      let entity = {
        epicId: this.epicId,
        name: newName,
      };
      this.http
        .put(`${this.epicsUrl}/${entity.epicId}`, entity, options)
        .subscribe(
          (resp: HttpResponse<any>) => {
            alert('Updated epic entity: ' + entity.epicId + 'to: ' + newName),
              window.location.reload();
          },
          (error) => {
            alert(
              'Epic ' + entity.epicId + ' has not been found in the database.'
            );
            window.location.reload();
          }
        );
    }
  }
}
