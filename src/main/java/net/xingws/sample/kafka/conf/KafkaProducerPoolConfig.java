/**
 * 
 */
package net.xingws.sample.kafka.conf;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author benxing
 *
 */
public class KafkaProducerPoolConfig {
	private String kafkaProducerProperties;
	private int maxPoolSize;
	private int maxIdleSize;
	private long minEvictableIdleTimeMillis;
	
	@Inject
	public KafkaProducerPoolConfig(@Named("kafka producer property file") String kafkaProducerProperties,
			@Named("max pool size") int maxPoolSize,
			@Named("max idle size") int maxIdleSize, 
			@Named("min duration as idle") long minEvictableIdleTimeMillis) {
		this.kafkaProducerProperties = kafkaProducerProperties;
		this.maxPoolSize = maxPoolSize;
		this.maxIdleSize = maxIdleSize;
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;		
	}
	
	/**
	 * @return the kafkaProducerProperties
	 */
	public String getKafkaProducerProperties() {
		return kafkaProducerProperties;
	}
	/**
	 * @param kafkaProducerProperties the kafkaProducerProperties to set
	 */
	public void setKafkaProducerProperties(String kafkaProducerProperties) {
		this.kafkaProducerProperties = kafkaProducerProperties;
	}
	/**
	 * @return the maxPoolSize
	 */
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	/**
	 * @return the maxIdleSize
	 */
	public int getMaxIdleSize() {
		return maxIdleSize;
	}
	/**
	 * @param maxIdleSize the maxIdleSize to set
	 */
	public void setMaxIdleSize(int maxIdleSize) {
		this.maxIdleSize = maxIdleSize;
	}
	/**
	 * @return the minEvictableIdleTimeMillis
	 */
	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	/**
	 * @param minEvictableIdleTimeMillis the minEvictableIdleTimeMillis to set
	 */
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	
}
