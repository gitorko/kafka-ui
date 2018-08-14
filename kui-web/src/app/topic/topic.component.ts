import { Component, OnInit } from '@angular/core';
import { KafkaService } from '../svc/kafka.service';
import { CreateTopic } from '../model/createtopic';
import { Topic } from '../model/topic';
import { Partition } from '../model/partition';
import { Broker } from '../model/broker';
import { Config } from '../model/config';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  createTopic: CreateTopic;
  topics: Topic[];
  brokers: Broker[];
  topicConfig: Config[];
  selectedTopic: Topic;
  partitions: Partition[];
  dialogWindow: boolean;

  constructor(private kafkaService: KafkaService) {
    this.createTopic = new CreateTopic();
    this.dialogWindow = false;
  }

  ngOnInit() {
    this.loadData();
  }

  loadData(): void {
    this.kafkaService.getTopic().subscribe(data => {
      this.topics = data;
    });
    this.kafkaService.getBroker().subscribe(data => {
      this.brokers = data;
    });
  }

  selectedChanged(topic) {
    if (topic != null) {
      this.selectedTopic = topic;
      this.kafkaService.getPartition(topic.name).subscribe(data => {
        this.partitions = data;
      });
      this.kafkaService.getTopicConfig(topic.name).subscribe(data => {
        this.topicConfig = data;
      });
    }
  }

  saveTopic(): void {
    this.kafkaService.createTopic(this.createTopic).subscribe(data =>{
      this.dialogWindow = false;
      this.loadData();
    });
  }
}
