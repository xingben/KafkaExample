/**
 * 
 */
package net.xingws.sample.kafka.producer;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author bxing
 *
 */
public class AlertingKafkaProducer {
	private KafkaProducer<String, String> producer;
	
	public AlertingKafkaProducer(Properties config) {
		this.producer = new KafkaProducer<String, String>(config);
	}
	
	public Future<RecordMetadata> send(String key, String value, String topic) {
		return this.producer.send(new ProducerRecord<>(topic, key, value));
	}
	
	public Future<RecordMetadata> sendAsync(String key, String value, String topic, Callback callback) {
		return this.producer.send(new ProducerRecord<>(topic, key, value), callback);
	}
	
	public void close() {
		this.producer.close();
	}
}
