/**
 * 
 */
package net.xingws.sample.kafka.app;

import com.google.inject.Guice;
import com.google.inject.Injector;

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
		
		for(int i=0; i<1000; ++i) {
			AlertingKafkaProducer producer = pool.getPool().borrowObject();
			producer.send("key"+i, "value"+i, "test");
			pool.getPool().returnObject(producer);
		}
		
		Thread.sleep(40000);
		System.out.println(pool.getPool().getNumActive());
		System.out.println(pool.getPool().getNumIdle());
		
		for(int i=0; i<10; ++i) {
			AlertingKafkaProducer producer = pool.getPool().borrowObject();
			producer.send("key"+i, "value"+i, "test");
			pool.getPool().returnObject(producer);
		}
		
		System.out.println(pool.getPool().getNumActive());
		System.out.println(pool.getPool().getNumIdle());
		
		pool.close();
	}

}
