package com.pchinodev.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange("deadLetterExchange");
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange("pchinodevExchange");
	}
	
	@Bean
	Queue dlq() {
		return QueueBuilder.durable("deadLetter.queue").build();
	}
	
	@Bean
	Queue queue() {
		return QueueBuilder.durable("pchinodev.queue").withArgument("x-dead-letter-exchange", "deadLetterExchange")
				.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}
	
	@Bean
	Binding dLQBinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
	}
	
	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("pchinodev");
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
