import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../model/topic';

@Injectable()
export class KafkaService {

  constructor(private http: HttpClient) {
  }

  public getTopic(): Observable<any> {
    return this.http.get('/api/topic');
  }

  public getTopicConfig(topicName): Observable<any> {
    return this.http.get(`/api/topic/${topicName}/config`);
  }

  public getPartition(topicName): Observable<any> {
    return this.http.get(`/api/topic/${topicName}/partition`);
  }

  public getBroker(): Observable<any> {
    return this.http.get('/api/broker');
  }

  public createTopic(createTopic): Observable<any> {
    return this.http.post('/api/topic', createTopic);
  }

  public getTopicMetrics(key): Observable<any> {
    return this.http.get(`/api/topic/metrics/${key}`);
  }

}
