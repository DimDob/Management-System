import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, Injectable, ViewChild } from '@angular/core';
import { EpicsTableComponent } from '../epics-table/epics-table.component';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { waitForAsync } from '@angular/core/testing';

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css'],
})
export class FeaturesComponent {
  @ViewChild(EpicsTableComponent) epicsTableComponent: EpicsTableComponent;

  displayedColumns: string[] = [
    'name',
    'description',
    'status',
    'owner',
    'stories',
  ];

  features: any;

  constructor(
    private http: HttpClient,
    private appComponent: AppComponent,
    private router: Router
  ) {}

  ngAfterViewInit() {
    const epicId = this.router.url.match('epics/(.*?)/')[1];
    this.showFeatures(epicId);
  }
  show(epicId: string) {
    this.showFeatures(epicId);
  }

  redirect(event:any){
    event.preventDefault()
    this.router.navigate(["/"]);
  }
  
  private showFeatures(epicId: string) {
    if (epicId.length === 36) {
      this.http.get(`http://localhost:8080/epics/${epicId}`).subscribe(
        (resp: HttpResponse<any>) => {
          const alertMsg = `Opening features of epic ${epicId}`;
          alert('Fetched epic:\n ' + alertMsg);
          this.fetchEpicData(epicId);
        },
        (error) => {
          alert('Epic ' + epicId + ' has not been found in the database.');
          window.location.reload();
        }
      );
    } else {
      alert('Please enter a valid UUID');
    }
  }

  private async fetchEpicData(epicId: string) {
    if (epicId.length != 36) {
      return;
    }
    this.http
      .get(`http://localhost:8080/epics/${epicId}/features`)
      .subscribe((response: HttpResponse<any>) => {
        this.features = response['content'];
      });
  }
}
