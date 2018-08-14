import { Component, OnInit } from '@angular/core';
import { KafkaService } from '../svc/kafka.service';
import { Broker } from '../model/broker';

@Component({
  selector: 'app-broker',
  templateUrl: './broker.component.html',
  styleUrls: ['./broker.component.css']
})
export class BrokerComponent implements OnInit {

  brokers: Broker[];

  constructor(private kafkaService: KafkaService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData(): void {
    this.kafkaService.getBroker().subscribe(data => {
      this.brokers = data;
    });
  }

}
