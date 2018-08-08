import { Component, OnInit } from '@angular/core';
import { KafkaService } from '../kafka.service';
import { Topic } from '../topic';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  topics: Array<Topic>[];
  
  constructor(private kafkaService: KafkaService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData(): void {
    this.kafkaService.getTopics().subscribe(data => {
      this.topics = data;
    });
  }

}
