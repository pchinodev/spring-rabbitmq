package com.pchinodev.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pchinodev.model.Book;

@RestController
@RequestMapping(value = "/pchinodev-rabbitmq/")
public class RabbitMQWebController {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@GetMapping(value = "/producer")
	public String producer(@RequestParam("bookID") String bookID, @RequestParam("title") String title, @RequestParam("price") int price) {
		Book book = new Book();
		book.setBookID(bookID);
		book.setTitle(title);
		book.setPrice(price);
		
		amqpTemplate.convertAndSend("pchinodevExchange", "pchinodev", book);
		return "Message sent to RabbitMQ successfully";
	}
}
