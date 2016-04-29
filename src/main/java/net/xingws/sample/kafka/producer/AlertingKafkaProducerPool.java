/**
 * 
 */
package net.xingws.sample.kafka.producer;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.pool2.impl.GenericObjectPool;

import net.xingws.sample.kafka.conf.KafkaProducerPoolConfig;


/**
 * @author bxing
 *
 */
@Singleton
public class AlertingKafkaProducerPool implements Closeable{
	private GenericObjectPool<AlertingKafkaProducer> pool;
	
	@Inject
	public AlertingKafkaProducerPool(KafkaProducerPoolConfig config) throws IOException {
		Properties properties = new Properties();
		InputStream input = AlertingKafkaProducerPool.class.getResourceAsStream(config.getKafkaProducerProperties());
		properties.load(input);

		pool = new GenericObjectPool<AlertingKafkaProducer>(new AlertingKafkaProducerFactory(properties));
		
		pool.setMaxTotal(config.getMaxPoolSize());
		pool.setMaxIdle(config.getMaxIdleSize());
		pool.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
	}

	
	public GenericObjectPool<AlertingKafkaProducer> getPool() {
		return this.pool;
	}


	@Override
	public void close() throws IOException {
		this.pool.close();
	}
}
