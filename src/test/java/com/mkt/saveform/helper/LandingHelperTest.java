package com.mkt.saveform.helper;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkt.core.base.BaseTest;
import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.PersonalData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {LandingHelper.class})
@ActiveProfiles("test")
public class LandingHelperTest extends BaseTest{

	@Autowired
	private LandingHelper landingHelper;
	
	
	@Test
	public void testToModel() {
		
		Landing entity = new Landing();
		entity.setFirstName("NAME");
		entity.setDateCreated(new Date());
		entity.setDateModified(new Date());
		
		LandingApplication model = landingHelper.toModel(entity);
		
		Assert.assertNotNull(model);
		Assert.assertEquals(entity.getFirstName(),model.getPersonalData().getFirstName());
	}
	
	@Test
	public void testToEntity() {
		
		LandingApplication model = new LandingApplication();
		model.setPersonalData(new PersonalData());
		model.getPersonalData().setFirstName("NAME");
		model.setDateModified("10/10/1990");
		
		Landing entity=landingHelper.toEntity(model);
		
		Assert.assertNotNull(entity);
		Assert.assertEquals(model.getPersonalData().getFirstName(), entity.getFirstName());
		
	}
	
	
	@Test
	public void testUpdateEntity() {
		
		LandingApplication model = new LandingApplication();
		model.setPersonalData(new PersonalData());
		model.getPersonalData().setFirstName("NAME2");
		
		Landing entity = new Landing();
		entity.setFirstName("NAME");
		
		landingHelper.toUpdateEntity(model, entity);
		Assert.assertEquals(model.getPersonalData().getFirstName(),entity.getFirstName());
	}
	
}
