package com.mkt.saveform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.service.LandingService;

@RestController
public class FormController {
	
	@Autowired
	private LandingService landingService;
	
	
	@GetMapping("/landing")
	public Object getLanding() {
		Message message = landingService.getLandings();
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@GetMapping("/landing/{folio}")
	public Object getLanding(@PathVariable("folio") String folio) {
		Message message = landingService.getLanding(folio);
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@PostMapping("/landing")
	public Object createLanding(@RequestBody LandingApplication app) {
		Message message=landingService.createLanding(app);
		return new ResponseEntity<>(message,message.getStatus());
	}

}