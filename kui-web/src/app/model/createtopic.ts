export class CreateTopic {
  topicName: String;
  partition: number;
  replication: number;
  config: [String, String];
}
