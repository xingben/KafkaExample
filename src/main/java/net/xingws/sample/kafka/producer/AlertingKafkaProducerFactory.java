/**
 * 
 */
package net.xingws.sample.kafka.producer;

import java.util.Properties;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author bxing
 *
 */
public class AlertingKafkaProducerFactory extends BasePooledObjectFactory<AlertingKafkaProducer>{
	
	private Properties config;
	
	public AlertingKafkaProducerFactory(Properties config) {
		this.config = config;
	}

	@Override
	public AlertingKafkaProducer create() throws Exception {
		
		return new AlertingKafkaProducer(config);
	}

	@Override
	public PooledObject<AlertingKafkaProducer> wrap(AlertingKafkaProducer producer) {
		// TODO Auto-generated method stub
		return new DefaultPooledObject<AlertingKafkaProducer>(producer);
	}

	@Override
	public void destroyObject(PooledObject<AlertingKafkaProducer> pool) throws Exception {
		pool.getObject().close();
		super.destroyObject(pool);
	}
}
