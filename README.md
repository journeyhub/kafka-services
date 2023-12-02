Follow below commands for kafka
1)from windows folder 
zookeeper-server-start.bat ..\..\config\zookeeper.properties  
2)from windows folder
kafka-server-start.bat ..\..\config\server.properties
3)from windows folder
kafka-topics.bat --bootstrap-server localhost:9092 --create --topic product-topic --partitions 3 --replication-factor 1
3)view list of topics 
kafka-topics.bat --bootstrap-server localhost:9092 --list
4)describe or view topic details
kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic product-topic
5)start producer console
kafka-console-producer.bat --broker-list locahost:9092 --topic product-topic
kafka-console-producer.bat --broker-list localhost:9092 --topic product-topic
6)consume from console
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic product-topic --from-beginning