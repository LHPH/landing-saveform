package com.mkt.saveform.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.mkt.core.base.BaseHelper;
import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.PersonalData;
import com.mkt.core.util.DateFunctionsUtil;
import com.mkt.core.util.ObjectFunctionsUtil;

@Component
public class LandingHelper extends BaseHelper<LandingApplication,Landing>{
	
	@Override
	public Landing toEntity(LandingApplication landing) {
		
		Landing entity = new Landing();
		
		entity.setFirstName(landing.getPersonalData().getFirstName());
		entity.setSecondName(landing.getPersonalData().getSecondName());
		entity.setLastName(landing.getPersonalData().getLastName());
		entity.setSecondLastName(landing.getPersonalData().getSecondLastName());
		entity.setEmail(landing.getPersonalData().getEmail());
		entity.setCellPhone(landing.getPersonalData().getCellPhone());
		entity.setHomePhone(landing.getPersonalData().getHomePhone());
		entity.setDateCreated(setDate(landing.getDateCreated()));
		entity.setDateModified(setDate(landing.getDateModified()));
		
		return entity;
	}
	
	@Override
	public LandingApplication toModel(Landing entity) {
		
		LandingApplication landing = new LandingApplication();
		landing.setPersonalData(new PersonalData());
		
		landing.setFolio(entity.getFolio());
		landing.setDateCreated(DateFunctionsUtil.dateToString(entity.getDateCreated()));
		landing.setDateModified(DateFunctionsUtil.dateToString(entity.getDateModified()));
		landing.getPersonalData().setFirstName(entity.getFirstName());
		landing.getPersonalData().setSecondName(entity.getSecondName());
		landing.getPersonalData().setLastName(entity.getLastName());
		landing.getPersonalData().setSecondLastName(entity.getSecondLastName());
		landing.getPersonalData().setEmail(entity.getEmail());
		landing.getPersonalData().setHomePhone(entity.getHomePhone());
		landing.getPersonalData().setCellPhone(entity.getCellPhone());
		
		return landing;
	}

	@Override
	public void toUpdateEntity(LandingApplication landing, Landing entity) {
		
		entity.setFirstName(landing.getPersonalData().getFirstName());
		entity.setSecondName(landing.getPersonalData().getSecondName());
		entity.setLastName(landing.getPersonalData().getLastName());
		entity.setSecondLastName(landing.getPersonalData().getSecondLastName());
		entity.setEmail(landing.getPersonalData().getEmail());
		entity.setCellPhone(landing.getPersonalData().getCellPhone());
		entity.setHomePhone(landing.getPersonalData().getHomePhone());
		entity.setDateModified(new Date());
		
	}
	
	private Date setDate(String date) {
		if(date==null) {
			return new Date();
		}
		else {
			return DateFunctionsUtil.stringToDate(date);
		}
	}

}
