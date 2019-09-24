package com.example.demo_kafka_logger.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ConsumerExample {

	private final static String TOPIC = "log-appender-topic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";
	private final static String GROUP_ID = "test-group3";

	public static void main(String[] args) throws InterruptedException {
		runConsumer();
	}

	private static Consumer<String, String> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Will start from the earliest (default latest)

		// Create the consumer using props.
		final Consumer<String, String> consumer = new KafkaConsumer<>(props);

		// Subscribe to the topicc
		consumer.subscribe(Collections.singletonList(TOPIC));
		return consumer;
	}

	static void runConsumer() throws InterruptedException {
		final Consumer<String, String> consumer = createConsumer();
		consumer.seekToBeginning(consumer.assignment());
		final int giveUp = 100;
		int noRecordsCount = 0;

		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
			
			if (consumerRecords.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp) {
					break;
				} else {
					continue;
				}
			}
			consumerRecords.forEach(record -> {
				System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", record.key(), record.value(),
						record.partition(), record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
		System.out.println("DONE");
	}
}