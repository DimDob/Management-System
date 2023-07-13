import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

export interface DialogData {
  name: string;
  assignee: string;
}

@Component({
  selector: 'app-PoPupComponent',
  templateUrl: 'create-epic-modal.component.html',
  styleUrls: ['create-epic-modal.component.css'],
})
export class CreateEpicModal {
  name: string;
  assignee: string;
  epicsUrl = 'http://localhost:8080/epics';

  constructor(public dialog: MatDialog, private http: HttpClient) {}

  createEpicEntity(): void {
    const dialogRef = this.dialog.open(CreateEpicModal, {
      data: { name: this.name },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.name = result;
    });

    this.createEpic(this.name, this.assignee);
  }

  create(name: string, assignee: string) {
    this.createEpic(name, assignee);
  }

  private createEpic(name: string, assignee: string) {
    const options = { headers: { 'Content-Type': 'application/json' } };

    const entity = {
      name: name,
      assignee: assignee,
    };

    const promptOnEmptyName = () => {
      const newName = prompt(
        'Please enter an epic entity name which is not empty'
      );
      if (newName.length !== 0) {
        entity['name'] = newName;
        return;
      } else {
        promptOnEmptyName();
      }
    };

    const pattern = new RegExp('.*?@{1}[A-Za-z]+\\.{1}(com|gmail|bg|cc)', 'g');

    const promptUnvalidEmail = () => {
      while (!pattern.test(entity['assignee'])) {
        const newEmail = prompt('Please enter a valid email');
        entity['assignee'] = newEmail;
      }
    };

    if (!entity.name) {
      promptOnEmptyName();
    }

    promptUnvalidEmail();

    this.http.post(this.epicsUrl, entity, options).subscribe(() => {
      alert('Created epic: ' + entity['name']);
      window.location.reload();
    });
  }
}
