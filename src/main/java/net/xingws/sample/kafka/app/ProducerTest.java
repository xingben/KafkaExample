/**
 * 
 */
package net.xingws.sample.kafka.app;

import org.apache.kafka.clients.producer.Callback;

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
		Callback callback = new ProducerCallback();
		
		for(int i=0; i<10; ++i) {
			AlertingKafkaProducer producer = pool.getPool().borrowObject();
			producer.sendAsync("key"+i, "value"+i, "test", callback);
			pool.getPool().returnObject(producer);
		}
		
		Thread.sleep(40000);
		System.out.println(pool.getPool().getNumActive());
		System.out.println(pool.getPool().getNumIdle());

		pool.close();
	}

}
