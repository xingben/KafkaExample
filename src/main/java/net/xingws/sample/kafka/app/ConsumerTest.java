/**
 * 
 */
package net.xingws.sample.kafka.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.xingws.sample.kafka.consumer.CustomKafkaConsumer;

/**
 * @author benxing
 *
 */
public class ConsumerTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		int numConsumers = 3;
		List<String> topics = new ArrayList<String>();
		topics.add("ttt.0");
		topics.add("ttt.1");		
		topics.add("hhh.0");
		
		ExecutorService executor = Executors.newFixedThreadPool(numConsumers);
		
		for(int i=0; i<numConsumers; ++i) {
			CustomKafkaConsumer<String, String> consumer = new CustomKafkaConsumer<String, String>("client"+i, "test_group", "/consumer-defaults.properties", topics, 100);
			executor.execute(consumer);
		}
		
		executor.awaitTermination(1, TimeUnit.DAYS);
	}
}
