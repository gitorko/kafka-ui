
#Still under development!
 
kafka-ui - Kafka UI Dashboard
====================
A UI dashboard that allows you to manage Kafa Cluster.

Requirements
====================
1. Requires Java 8. 
2. Node.js NPM
2. Compatible with Kafka 2.0.0 version only.

Setup
====================
1. Copy the application.properties file and edit it to point to your kafka cluster, JMX must be enabled.
2. cd kui-web; ng build --prod
3. cd kui-service;mvn clean install
4. Run the jar. ( nohup java -jar kui-service-1.0.0-SNAPSHOT.jar.jar -Dspring.config.location=application.properties & )
5. <a href="http://localhost:8080">http://localhost:8080</a>

Screenshots
====================
Dashboard
<br/>
<img src="https://raw.github.com/gitorko/kafka-ui/master/images/Capture1.PNG"/>
<br/>

Topic
<br/>
<img src="https://raw.github.com/gitorko/kafka-ui/master/images/Capture2.PNG"/>
<br/>

Create Topic
<br/>
<img src="https://raw.github.com/gitorko/kafka-ui/master/images/Capture3.PNG"/>
<br/>

Broker
<br/>
<img src="https://raw.github.com/gitorko/kafka-ui/master/images/Capture4.PNG"/>
<br/>

License & Contribution
====================

kafka-ui is released under the Apache 2.0 license. Comments, bugs, pull requests, and other contributions are all welcomed!

<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
