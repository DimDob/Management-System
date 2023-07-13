import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CreateEpicModal } from '../createEpicModal/create-epic-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-epic-modal',
  templateUrl: './delete-epic-modal.component.html',
  styleUrls: ['./delete-epic-modal.component.css'],
})
export class DeleteEpicModalComponent {
  epicId: string;
  epicsUrl = 'http://localhost:8080/epics';

  constructor(
    public dialog: MatDialog,
    private http: HttpClient,
    private router: Router
  ) {}

  createEpicEntity(): void {
    const dialogRef = this.dialog.open(CreateEpicModal, {
      data: { epicId: this.epicId },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.epicId = result;
    });
    this.deleteEpic();
  }

  deleteEpic() {
    this.deleteEpicById();
  }

  private deleteEpicById() {
    this.http.delete(`${this.epicsUrl}/${this.epicId}`).subscribe(
      (resp: HttpResponse<any>) => {
        alert('Deleted epic: ' + this.epicId), window.location.reload();
        this.router.navigate(['/epics']);
      },
      (error) => {
        alert('Epic ' + this.epicId + ' has not been found in the database.');
        window.location.reload();
      }
    );
  }
}
