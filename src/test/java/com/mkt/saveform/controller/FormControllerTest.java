package com.mkt.saveform.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.core.model.PersonalData;
import com.mkt.saveform.service.LandingService;

import static com.mkt.saveform.util.Constants.ENDPOINT_LANDING;
import static com.mkt.saveform.util.Constants.ENDPOINT_LANDING_FOLIO;

@RunWith(SpringRunner.class)
@WebMvcTest(FormController.class)
@ActiveProfiles("test")
public class FormControllerTest {
	
	@Autowired
    private MockMvc mvc; 
	
	@MockBean
	private LandingService landingService;
	
	public static final String FOLIO="323";
	private LandingApplication landing;
	
	JacksonTester<Object> jsonTester;
	
	@Before
	public void setUp() {
		
		 ObjectMapper objectMapper = new ObjectMapper();
         JacksonTester.initFields(this, objectMapper);
         
        landing = new LandingApplication();
 		landing.setPersonalData(new PersonalData());
 		landing.getPersonalData().setFirstName("NAME");
		
	}
	
	@Test
	public void testGetLanding() throws Exception{
		
		Message msg = new Message();
		msg.setStatus(HttpStatus.OK);
		
		given(landingService.getLandings()).willReturn(msg);
		
		MvcResult result=mvc.perform(get(ENDPOINT_LANDING)).andDo(print()).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void testGetLandingByFolio() throws Exception{
		
		Message msg = new Message();
		msg.setStatus(HttpStatus.OK);
		
		given(landingService.getLanding(any(String.class))).willReturn(msg);
		
		MvcResult result=mvc.perform(get(ENDPOINT_LANDING_FOLIO,FOLIO))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void testCreateLanding() throws Exception{
		
		Message msg = new Message();
		msg.setStatus(HttpStatus.OK);
		
		String request = jsonTester.write(landing).getJson();
		
		given(landingService.createLanding(any(LandingApplication.class))).willReturn(msg);
		
		MvcResult result=mvc.perform(post(ENDPOINT_LANDING)
				.contentType(MediaType.APPLICATION_JSON).content(request))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		
	}
	
	@Test
	public void testUpdateLanding() throws Exception{
		
		Message msg = new Message();
		msg.setStatus(HttpStatus.OK);
		
		String request = jsonTester.write(landing).getJson();
		
		given(landingService.updateLanding(any(LandingApplication.class),any(String.class))).willReturn(msg);
		
		MvcResult result=mvc.perform(put(ENDPOINT_LANDING_FOLIO,FOLIO)
				.contentType(MediaType.APPLICATION_JSON).content(request))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		
	}

	@Test
	public void testDeleteLanding() throws Exception{
		
		Message msg = new Message();
		msg.setStatus(HttpStatus.OK);
		
		given(landingService.deleteLanding(any(String.class))).willReturn(msg);
		
		MvcResult result=mvc.perform(delete(ENDPOINT_LANDING_FOLIO,FOLIO)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		
	}

}
