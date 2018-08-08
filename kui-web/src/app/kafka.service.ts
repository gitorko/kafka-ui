import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class KafkaService {

  constructor(private http: HttpClient) {
  }

  public getTopics(): Observable<any> {
    return this.http.get('/api/topics');
  }
}