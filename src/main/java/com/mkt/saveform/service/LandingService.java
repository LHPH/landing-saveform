package com.mkt.saveform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mkt.core.base.BaseRegularExpression;
import com.mkt.core.config.MessageConfig;
import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.core.validators.RegularExpressionRule;
import com.mkt.saveform.helper.LandingHelper;
import com.mkt.saveform.repository.LandingRepository;

import static com.mkt.core.constants.ConstantsRegularExpression.REGEX_NUMERICS;
import static com.mkt.saveform.util.Constants.MKT_008;

@Service
public class LandingService {
	
	@Autowired
	private LandingRepository landingRepository;
	
	@Autowired
	private LandingHelper landingHelper;
	
	@Autowired
	private RegularExpressionRule regularExpressionRule;
	
	@Autowired
	private MessageConfig messageConfig;
	
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
		
		BaseRegularExpression exp = new BaseRegularExpression();
		exp.setExpression(REGEX_NUMERICS);
		exp.setMaxLength(10);
		exp.setMinLength(1);
		exp.setOptional(false);
		
		Message result=regularExpressionRule.evaluate(folio,exp);
		Boolean ind = (Boolean)result.getData();
		if(!ind) {
			Message msg=messageConfig.getMessage(MKT_008);
			msg.setData(folio);
			msg.setDescription(result.getDescription());
			result.setData(msg).setStatus(HttpStatus.BAD_REQUEST);
			return result;
		}
		
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
			return new Message().setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Message updateLanding(LandingApplication landing,String folio) {
		
		try {
			Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
			if(element.isPresent()) {
				Landing entity = element.get();
				landingHelper.toUpdateEntity(landing, entity);
				landingRepository.save(entity);
				return new Message().setStatus(HttpStatus.OK);
			}
			else {
				return createLanding(landing);
			}
		}
		catch(Exception e) {
			return new Message().setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public Message deleteLanding(String folio) {
		
		try {
			Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
			if(element.isPresent()) {
				landingRepository.deleteById(Integer.valueOf(folio));
				return new Message().setStatus(HttpStatus.OK);
			}
			else {
				return new Message().setStatus(HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			return new Message().setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
