/**
 * 
 */
package net.xingws.sample.kafka.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author benxing
 *
 */
public class ProducerCallback implements Callback {
	private static Logger log = LoggerFactory.getLogger(ProducerCallback.class);
	/* (non-Javadoc)
	 * @see org.apache.kafka.clients.producer.Callback#onCompletion(org.apache.kafka.clients.producer.RecordMetadata, java.lang.Exception)
	 */
	@Override
	public void onCompletion(RecordMetadata metaData, Exception ex) {
		if(ex != null) {
			log.error(ex.getMessage());
		}else{
			log.info("topic={}   paritition={}   offset={}" , metaData.topic(), metaData.partition(), metaData.offset());
		}

	}

}
