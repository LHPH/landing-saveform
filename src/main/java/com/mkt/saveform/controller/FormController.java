package com.mkt.saveform.controller;

import static com.mkt.saveform.constants.Constants.ENDPOINT_LANDING;
import static com.mkt.saveform.constants.Constants.ENDPOINT_LANDING_FOLIO;
import static com.mkt.saveform.constants.Constants.MKT_000;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkt.core.base.BaseRestController;
import com.mkt.core.config.MessageConfig;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.service.LandingService;

@RestController
public class FormController extends BaseRestController{
	
	@Autowired
	private LandingService landingService;
	
	@Autowired
	private MessageConfig config;
	
	
	@GetMapping(ENDPOINT_LANDING)
	public Object getLanding() {
		getLogger().info("Consultando landings");
		Message message = landingService.getLandings();
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@GetMapping(ENDPOINT_LANDING_FOLIO)
	public Object getLanding(@PathVariable("folio") String folio) {
		getLogger().info("Consultando landing con folio {}",folio);
		Message message = landingService.getLanding(folio);
		return new ResponseEntity<>(message.getData(),message.getStatus());
	}
	
	@PostMapping(ENDPOINT_LANDING)
	public Object createLanding(@RequestBody LandingApplication app) {
		getLogger().info("Creando una nueva landing");
		Message message=landingService.createLanding(app);
		return new ResponseEntity<>(message,message.getStatus());
	}
	
	@PutMapping(ENDPOINT_LANDING_FOLIO)
	public Object updateLanding(@RequestBody LandingApplication app,@PathVariable("folio") String folio) {
		getLogger().info("Actualizando landing");
		Message message=landingService.updateLanding(app,folio);
		return new ResponseEntity<>(message,message.getStatus());
	}
	
	@DeleteMapping(ENDPOINT_LANDING_FOLIO)
	public Object deleteLanding(@PathVariable("folio") String folio) {
		getLogger().info("Eliminando la landing con el folio {}",folio);
		Message message=landingService.deleteLanding(folio);
		return new ResponseEntity<>(message,message.getStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Message> sendError(Exception e){
		
		getLogger().error("Ocurrio un error en la aplicacion {}",e);
		Message msg=config.getMessage(MKT_000);
		
		Message aux = new Message();
		aux.setCode(msg.getCode());
		aux.setType(msg.getType());
		aux.setDescription(msg.getDescription()+": "+e.getMessage());
		aux.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}