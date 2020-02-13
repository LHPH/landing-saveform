package com.mkt.saveform.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.helper.LandingHelper;
import com.mkt.saveform.repository.LandingRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= {LandingService.class})
@ActiveProfiles("test")
public class LandingServiceTest {

	@Autowired
	private LandingService landingService;
	
	@MockBean
	private LandingRepository landingRepository;
	
	@MockBean
	private LandingHelper landingHelper;
	
	public static final String FOLIO="121";
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGetLandingsEmpty() {
		
		given(landingRepository.findAll()).willReturn(new ArrayList<>());
		
		Message msg=landingService.getLandings();
		
		Assert.assertEquals(HttpStatus.NOT_FOUND, msg.getStatus());
		Assert.assertNotNull(msg.getData());
		
	}
	
	@Test
	public void testGetLandings() {
		
		List<Landing> list = new ArrayList<>();
		
		list.add(new Landing());
		
		given(landingRepository.findAll()).willReturn(list);
		
		Message msg=landingService.getLandings();
		
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
		Assert.assertNotNull(msg.getData());
		
	}
	
	@Test
	public void testGetLandingByFolioNotFound() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.empty());
		
		Message msg = landingService.getLanding(FOLIO);
		Assert.assertEquals(HttpStatus.NOT_FOUND, msg.getStatus());
		//Assert.assertNotNull(msg.getData());
		
	}
	
	@Test
	public void testGetLandingByFolio() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.of(new Landing()));
		given(landingHelper.toModel(any(Landing.class))).willReturn(new LandingApplication());
		
		Message msg = landingService.getLanding(FOLIO);
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
		Assert.assertNotNull(msg.getData());
		
	}
	
	@Test
	public void testCreateLanding() {
		
		given(landingRepository.save(any(Landing.class))).willReturn(new Landing());
		given(landingHelper.toEntity(any(LandingApplication.class))).willReturn(new Landing());
		
		Message msg = landingService.createLanding(new LandingApplication());
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
	}
	
	@Test
	public void testCreateLandingError() {
		
		given(landingHelper.toEntity(any(LandingApplication.class))).willReturn(new Landing());
		doThrow(new RuntimeException()).when(landingRepository).save(any(Landing.class));
		
		Message msg = landingService.createLanding(new LandingApplication());
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, msg.getStatus());
		
		
	}
	
	@Test
	public void testUpdateLanding() {
		
		given(landingRepository.findById(any())).willReturn(Optional.of(new Landing()));
		given(landingHelper.toEntity(any(LandingApplication.class))).willReturn(new Landing());
		
		Message msg = landingService.updateLanding(new LandingApplication(),FOLIO);
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
		
	}
	
	@Test
	public void testCreateLandingByUpdate() {
		
		given(landingRepository.findById(any())).willReturn(Optional.empty());
		given(landingRepository.save(any(Landing.class))).willReturn(new Landing());
		given(landingHelper.toEntity(any(LandingApplication.class))).willReturn(new Landing());
		
		Message msg = landingService.updateLanding(new LandingApplication(),FOLIO);
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
	}
	
	@Test
	public void testUpdateLandingError() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.of(new Landing()));
		given(landingHelper.toEntity(any(LandingApplication.class))).willReturn(new Landing());
		doThrow(new RuntimeException()).when(landingRepository).save(any(Landing.class));
		
		Message msg = landingService.updateLanding(new LandingApplication(),FOLIO);
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, msg.getStatus());
		
		
	}
	
	@Test
	public void testDeleteLandingNotFound() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.empty());
		
		Message msg = landingService.deleteLanding(FOLIO);
		Assert.assertEquals(HttpStatus.NOT_FOUND, msg.getStatus());
		//Assert.assertNotNull(msg.getData());
		
	}
	
	@Test
	public void testDeleteLanding() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.of(new Landing()));
		
		Message msg = landingService.deleteLanding(FOLIO);
		Assert.assertEquals(HttpStatus.OK, msg.getStatus());
		
	}
	
	@Test
	public void testDeleteLandingError() {
		
		given(landingRepository.findById(any(Integer.class))).willReturn(Optional.of(new Landing()));
		doThrow(new RuntimeException()).when(landingRepository).deleteById(any(Integer.class));
		
		Message msg = landingService.deleteLanding(FOLIO);
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, msg.getStatus());
		
		
	}
}
