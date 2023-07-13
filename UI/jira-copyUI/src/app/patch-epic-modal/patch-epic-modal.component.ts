import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CreateEpicModal } from '../createEpicModal/create-epic-modal.component';

@Component({
  selector: 'app-patch-epic-modal',
  templateUrl: './patch-epic-modal.component.html',
  styleUrls: ['./patch-epic-modal.component.css'],
})
export class PatchEpicModalComponent {
  epicId: string;
  patchParams: string;
  epicsUrl = 'http://localhost:8080/epics';

  constructor(
    public dialog: MatDialog,
    private http: HttpClient,
    private router: Router
  ) {}

  patchEpicEntity(): void {
    const dialogRef = this.dialog.open(PatchEpicModalComponent, {
      data: { epicId: this.epicId },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.epicId = result.epicId;
    });
    this.patch();
  }

  patch() {
    this.patchEpicById();
  }

  private patchEpicById() {
    if (
      this.epicId === undefined ||
      this.patchParams === undefined ||
      this.epicId.length != 36 ||
      this.patchParams.length == 0
    ) {
      alert('Please enter valid input');
    } else {
      const body = {
        name: this.patchParams,
      };

      this.http.patch(`${this.epicsUrl}/${this.epicId}`, body).subscribe(
        (resp: HttpResponse<any>) => {
          alert(
            'Patched epic: ' +
              this.epicId +
              '\n' +
              'with patch parameters: ' +
              this.patchParams
          );
          window.location.reload();
        },
        (error) => {
          alert('Epic ' + this.epicId + ' has not been found in the database.');
          window.location.reload();
        }
      );
    }
  }
}
