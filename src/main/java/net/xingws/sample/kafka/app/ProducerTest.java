/**
 * 
 */
package net.xingws.sample.kafka.app;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.xingws.sample.kafka.callback.ProducerCallback;
import net.xingws.sample.kafka.guice.KafkaProducerPoolConfigModule;
import net.xingws.sample.kafka.producer.AlertingKafkaProducer;
import net.xingws.sample.kafka.producer.AlertingKafkaProducerPool;

/**
 * @author benxing
 *
 */
public class ProducerTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Injector injector = Guice.createInjector(new KafkaProducerPoolConfigModule());
		
		AlertingKafkaProducerPool pool = injector.getInstance(AlertingKafkaProducerPool.class);
		
		for(int i=0; i<100000; ++i) {
			AlertingKafkaProducer producer = pool.getPool().borrowObject();
			ProducerRecord<String, String> record = new ProducerRecord<String,String>("hhh.0", "test"+i, "test"+i);
			ProducerRecord<String, String> record1 = new ProducerRecord<String,String>("ttt.0", "testgroup"+i, "testgroup"+i);
			ProducerRecord<String, String> record2 = new ProducerRecord<String,String>("ttt.1", "group"+i, "group"+i);
			producer.sendAsync(record, new ProducerCallback(record));
			producer.sendAsync(record1, new ProducerCallback(record1));
			producer.sendAsync(record2, new ProducerCallback(record2));
			pool.getPool().returnObject(producer);
		}
		
		Thread.sleep(40000);
		System.out.println(pool.getPool().getNumActive());
		System.out.println(pool.getPool().getNumIdle());

		pool.close();
	}

}
