package com.mkt.saveform.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.service.LandingService;
import static com.mkt.saveform.util.Constants.ENDPOINT_LANDING;
import static com.mkt.saveform.util.Constants.ENDPOINT_LANDING_FOLIO;

@RestController
public class FormController {
	
	@Autowired
	private LandingService landingService;
	
	public Logger logger = LogManager.getLogger(getClass());
	
	
	@GetMapping(ENDPOINT_LANDING)
	public Object getLanding() {
		logger.info("Hola Mundo");
		Message message = landingService.getLandings();
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@GetMapping(ENDPOINT_LANDING_FOLIO)
	public Object getLanding(@PathVariable("folio") String folio) {
		Message message = landingService.getLanding(folio);
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@PostMapping(ENDPOINT_LANDING)
	public Object createLanding(@RequestBody LandingApplication app) {
		Message message=landingService.createLanding(app);
		return new ResponseEntity<>(message,message.getStatus());
	}
	
	@PutMapping(ENDPOINT_LANDING_FOLIO)
	public Object updateLanding(@RequestBody LandingApplication app,@PathVariable("folio") String folio) {
		Message message=landingService.updateLanding(app,folio);
		return new ResponseEntity<>(message,message.getStatus());
	}
	
	@DeleteMapping(ENDPOINT_LANDING_FOLIO)
	public Object deleteLanding(@PathVariable("folio") String folio) {
		Message message=landingService.deleteLanding(folio);
		return new ResponseEntity<>(message,message.getStatus());
	}
	

}