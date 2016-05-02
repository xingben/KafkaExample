/**
 * 
 */
package net.xingws.sample.kafka.consumer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author benxing
 *
 */
public class CustomKafkaConsumer<K, V> implements ConsumerRebalanceListener, Closeable, Runnable {
	private static Logger log = LoggerFactory.getLogger(CustomKafkaConsumer.class);
	private KafkaConsumer<K, V> consumer;
	private long timeout;

	public CustomKafkaConsumer(String clientId, String consumerGroup, String properties, List<String> topics,
			long timeout) throws IOException {
		Properties props = new Properties();
		try {
			props.load(CustomKafkaConsumer.class.getResourceAsStream(properties));
		} catch (IOException e) {
			log.error("failed to load the consumer properties {}", properties);
			throw e;
		}
		props.put("client.id", clientId);
		props.put("group.id", consumerGroup);
		this.consumer = new KafkaConsumer<K, V>(props);
		this.consumer.subscribe(topics);
		this.timeout = timeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.kafka.clients.consumer.ConsumerRebalanceListener#
	 * onPartitionsRevoked(java.util.Collection)
	 */
	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		log.info("Recieve the revoked event, save the work");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.kafka.clients.consumer.ConsumerRebalanceListener#
	 * onPartitionsAssigned(java.util.Collection)
	 */
	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		log.info("Recieve the new partition event, save the work");

	}

	@Override
	public void close() throws IOException {
		this.consumer.wakeup();
	}

	@Override
	public void run() {

		try {
			log.info("start a consumer");
			while (true) {
				ConsumerRecords<K, V> records = consumer.poll(timeout);
				for (ConsumerRecord<K, V> record : records) {
					log.info("process record topic = {}  partition = {}  offset={}  key={} value={}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}

				this.consumer.commitAsync();
			}
		} catch (WakeupException e) {
			log.info("recieve wakeup exception");
		} finally {
			this.consumer.commitSync();
			this.consumer.close();
		}
	}
}
