/**
 * 
 */
package net.xingws.sample.kafka.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import net.xingws.sample.kafka.conf.KafkaProducerPoolConfig;

/**
 * @author benxing
 *
 */
public class KafkaProducerPoolConfigModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		this.bind(String.class).annotatedWith(Names.named("kafka producer property file")).toInstance("/producer-defaults.properties");
		this.bind(Integer.class).annotatedWith(Names.named("max pool size")).toInstance(20);
		this.bind(Integer.class).annotatedWith(Names.named("max idle size")).toInstance(20);
		this.bind(Long.class).annotatedWith(Names.named("min duration as idle")).toInstance(30000L);
		
		this.bind(KafkaProducerPoolConfig.class).in(Singleton.class);
	}
}
