package com.mkt.saveform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.helper.LandingHelper;
import com.mkt.saveform.repository.LandingRepository;

@Service
public class LandingService {
	
	@Autowired
	private LandingRepository landingRepository;
	
	@Autowired
	private LandingHelper landingHelper;
	
	public Message getLandings() {
		
		List<Landing> list=landingRepository.findAll();
		HttpStatus status = null;
		
		if(list.isEmpty()) {
			status= HttpStatus.NOT_FOUND;
		}
		else {
			status = HttpStatus.OK;
		}
		
		return new Message().setData(list).setStatus(status);
		
	}
	
	
	public Message getLanding(String folio) {
		
		Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
		
		if(element.isPresent()) {
			LandingApplication landing = landingHelper.toModel(element.get());
			return new Message().setData(landing).setStatus(HttpStatus.OK);
		}
		else {
			return new Message().setStatus(HttpStatus.NOT_FOUND);
		}
		
	}
	
	public Message createLanding(LandingApplication landing) {
		
		try {
			Landing entity=landingHelper.toEntity(landing);
			landingRepository.save(entity);
			return new Message().setStatus(HttpStatus.OK);
		}
		catch(Exception e) {
			return new Message().setDescription(e.getMessage()).setStatus(HttpStatus.INSUFFICIENT_STORAGE);
		}
	}

}
