import { Broker } from './broker';

export class Partition {
  partition: Number;
  leader: Broker;
  replicas: Broker[];
  isr: Broker[];

}
