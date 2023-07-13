import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Epic } from './epic';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-epics-table',
  templateUrl: './epics-table.component.html',
  styleUrls: ['./epics-table.component.css'],
})
export class EpicsTableComponent {
  displayedColumns: string[] = ['number', 'name', 'assignee'];
  dataSource: MatTableDataSource<Epic>;
  features: any[];
  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(
    private http: HttpClient,
    private appComponent: AppComponent,
    private router: Router
  ) {
    this.dataSource = new MatTableDataSource<Epic>([]);
    this.get();
    
  }

  private getAll(epicsUrl: string) {
    let epics: Epic[] = [];
    this.http.get(epicsUrl).subscribe((data: any) => {
      const rows = data.content;
      rows.forEach((row: any) => {
        const epic = new Epic();
        epic.epicId = row.epicId;
        epic.name = row.name;
        epic.assignee = row.assignee;
        epics.push(epic);
      });
      this.dataSource.data = epics;
      this.dataSource.paginator = this.paginator;
      this.paginator.length = 1000
    });
  }

  get() {
    this.getAll('http://localhost:8080/epics');
  }

  show(epicId: string) {
    this.router.navigate(['/epics', epicId, 'features']);
  }
}
