package com.example.demo_kafka_logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author v.laluashvili
 * 
 * @see <a href=
 *      "https://www.devglan.com/apache-kafka/stream-log4j-logs-to-kafka">How to
 *      log to Kafka</a>
 */
@SpringBootApplication
public class DemoKafkaLoggerApplication implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoKafkaLoggerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoKafkaLoggerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		while (true) {
			LOGGER.info("Inside scheduleTask - Sending logs to Kafka at "
					+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			Thread.sleep(3000);
		}
	}

}
