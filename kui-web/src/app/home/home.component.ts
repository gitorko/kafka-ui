import { Component, OnInit } from '@angular/core';
import { KafkaService } from '../svc/kafka.service';
import { Topic } from '../model/topic';
import { Broker } from '../model/broker';
import { Chart } from 'chart.js';
import 'chartjs-plugin-streaming';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  topics: Topic[];
  brokers: Broker[];
  chart1: any;
  chart2: any;

  constructor(private kafkaService: KafkaService) { }

  ngOnInit() {
    this.loadData();
    this.loadCharts(this.kafkaService);
  }

  loadData(): void {
    this.kafkaService.getTopic().subscribe(data => {
      this.topics = data;
    });
    this.kafkaService.getBroker().subscribe(data => {
      this.brokers = data;
    });
  }

  loadCharts(kafkaSvc) {
    this.chart1 = new Chart('chart1', {
      responsive: false,
      maintainAspectRatio: false,
      width: 400,
      height: 150,
      type: 'line',
      data: {
        datasets: [{
          lineTension: 0,
          pointRadius: 0,
          backgroundColor: '#49AFD9',
          data: []
        }]
      },
      options: {
        scales: {
          xAxes: [{
            type: 'realtime',
            gridLines: {
              display: false
            }
          }],
          yAxes: [{
            gridLines: {
              display: false
            },
            ticks: {
              stepSize: 5
            }
          }]
        },
        legend: {
          display: false,
        },
        plugins: {
          streaming: {
            onRefresh: function (chart) {
              kafkaSvc.getTopicMetrics('BytesInPerSec').subscribe(jmxdata => {
                chart.data.datasets[0].data.push({
                  x: Date.now(),
                  y: Math.round(jmxdata.oneminuterate)
                });
              });
            },
            duration: 300000,
            refresh: 6000,
            delay: 3000,
            frameRate: 5,
            pause: false
          }
        }
      }
    });

    this.chart2 = new Chart('chart2', {
      responsive: false,
      maintainAspectRatio: false,
      width: 400,
      height: 150,
      type: 'line',
      data: {
        datasets: [{
          lineTension: 0,
          pointRadius: 0,
          backgroundColor: '#49AFD9',
          data: []
        }]
      },
      options: {
        scales: {
          xAxes: [{
            type: 'realtime',
            gridLines: {
              display: false
            }
          }],
          yAxes: [{
            gridLines: {
              display: false
            },
            ticks: {
              stepSize: 5
            }
          }]
        },
        legend: {
          display: false,
        },
        plugins: {
          streaming: {
            onRefresh: function (chart) {
              kafkaSvc.getTopicMetrics('BytesOutPerSec').subscribe(jmxdata => {
                chart.data.datasets[0].data.push({
                  x: Date.now(),
                  y: Math.round(jmxdata.oneminuterate)
                });
              });
            },
            duration: 300000,
            refresh: 6000,
            delay: 3000,
            frameRate: 5,
            pause: false
          }
        }
      }
    });

  }

}
