package com.mkt.saveform.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkt.core.base.BaseTest;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.core.model.PersonalData;
import com.mkt.core.validators.RegularExpressionRule;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {ValidateLandingService.class,RegularExpressionRule.class})
@ActiveProfiles("test")
public class ValidateLandingServiceTest extends BaseTest{
	
	@Autowired
	private ValidateLandingService validateLandingService;
	
	private LandingApplication landing;
	
	@Before
	public void setUp() {
		
		landing = new LandingApplication();
		landing.setPersonalData(new PersonalData());
		
		landing.getPersonalData().setFirstName("JOEL");
		landing.getPersonalData().setSecondName("HUMBERTO");
		landing.getPersonalData().setLastName("HANAZAWA");
		landing.getPersonalData().setSecondLastName("RODRIGUEZ");
		landing.getPersonalData().setEmail("dev.test@outlook.com");
		landing.getPersonalData().setHomePhone("5578968790");
		landing.getPersonalData().setCellPhone("5578968790");
	}
	
	@Test
	public void testValidateFolio() {
		
		Message msg = validateLandingService.validateFolio("231");
		Assert.assertTrue((Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateFirstName() {
		
		Message msg = validateLandingService.validateFirstName(landing.getPersonalData().getFirstName());
		Assert.assertTrue((Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateSecondName() {
		
		Message msg = validateLandingService.validateSecondName(null);
		Assert.assertTrue((Boolean)(msg.getData()));
		
	}
	
	@Test
	public void testValidateLastName() {
		
		Message msg = validateLandingService.validateLastName(landing.getPersonalData().getLastName());
		Assert.assertTrue(msg.getDescription(),(Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateSecondLastName() {
	
		Message msg = validateLandingService.validateSecondLastName("");
		Assert.assertTrue(msg.getDescription(),(Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateEmail() {
		
		Message msg = validateLandingService.validateEmail(landing.getPersonalData().getEmail());
		Assert.assertTrue(msg.getDescription(),(Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateHomephone() {
		
		Message msg = validateLandingService.validateHomePhone(landing.getPersonalData().getHomePhone());
		Assert.assertTrue(msg.getDescription(),(Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateCellphone() {
		
		Message msg = validateLandingService.validateCellPhone(landing.getPersonalData().getCellPhone());
		Assert.assertTrue(msg.getDescription(),(Boolean)(msg.getData()));
	}
	
	@Test
	public void testValidateNullSectionPersonalData() {
		
		Message msg = validateLandingService.validatePersonalData(null);
		Assert.assertTrue(!((Boolean)(msg.getData())));
		
	}
	
	@Test
	public void testValidatePersonalDataHP() {
		
		Message msg = validateLandingService.validatePersonalData(landing.getPersonalData());
		Assert.assertTrue(msg.getDescription(),((Boolean)(msg.getData())));
	}

}
