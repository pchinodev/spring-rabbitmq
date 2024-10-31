package com.pchinodev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.pchinodev.exception.InvalidSalaryException;
import com.pchinodev.model.Book;

@Component
public class RabbitMQConsumer {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
	
	@RabbitListener(queues = "pchinodev.queue")
	public void recievedMessage(Book book) throws InvalidSalaryException {
		logger.info("Recieved Message from RabbitMQ: " + book);
		if (book.getPrice() < 0) {
			throw new InvalidSalaryException();
		}
	}
}
