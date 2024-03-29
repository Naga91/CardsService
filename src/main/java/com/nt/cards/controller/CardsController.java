package com.nt.cards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nt.cards.config.CardsServiceConfig;
import com.nt.cards.model.Cards;
import com.nt.cards.model.Customer;
import com.nt.cards.model.Properties;
import com.nt.cards.repository.CardsRepository;

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CardsServiceConfig cardsConfig;

	@PostMapping("myCards")
	public List<Cards> getCardDetils(@RequestHeader("nt-trace-id") String traceId, @RequestBody Customer customer) {
		System.out.println("Inside Cards service, traceId: " + traceId);
		List<Cards> cards = this.cardsRepository.findByCustomerId(customer.getCustomerId());
		if (cards != null) {
			return cards;
		} else {
			return null;
		}
	}

	@GetMapping("/cards/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(), 
				cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
		String response = ow.writeValueAsString(properties);
		return response;
	}
}
