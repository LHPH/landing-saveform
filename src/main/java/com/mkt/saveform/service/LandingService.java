package com.mkt.saveform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mkt.core.base.BaseService;
import com.mkt.core.config.MessageConfig;
import com.mkt.core.entity.Landing;
import com.mkt.core.model.LandingApplication;
import com.mkt.core.model.Message;
import com.mkt.saveform.helper.LandingHelper;
import com.mkt.saveform.repository.LandingRepository;

import static com.mkt.saveform.constants.Constants.MKT_001;
import static com.mkt.saveform.constants.Constants.MKT_002;
import static com.mkt.saveform.constants.Constants.MKT_003;
import static com.mkt.saveform.constants.Constants.MKT_004;
import static com.mkt.saveform.constants.Constants.MKT_005;
import static com.mkt.saveform.constants.Constants.MKT_006;
import static com.mkt.saveform.constants.Constants.MKT_007;
import static com.mkt.saveform.constants.Constants.MKT_008;

@Service
public class LandingService extends BaseService{
	
	@Autowired
	private LandingRepository landingRepository;
	
	@Autowired
	private LandingHelper landingHelper;
	
	@Autowired
	private ValidateLandingService validateLandingService;
	
	@Autowired
	private MessageConfig messageConfig;
	
	public Message getLandings() {
		
		List<Landing> list=landingRepository.findAll();
		HttpStatus status = null;
		
		if(list.isEmpty()) {
			getLogger().info("No se encontraron landings en la BD");
			Message msg=messageConfig.getMessage(MKT_002);
			status= HttpStatus.NOT_FOUND;
			return msg.setData(list).setStatus(status);
		}
		else {
			getLogger().info("Se encontraron {} landings en la BD",list.size());
			status = HttpStatus.OK;
			return new Message().setData(list).setStatus(status);
		}
		
	}
	
	
	public Message getLanding(String folio) {
		
		Message result=validateLandingService.validateFolio(folio);
		Boolean ind = (Boolean)result.getData();
		if(!ind) {
			
			getLogger().warn("El folio ingresado no paso las validaciones correspondientes");
			Message msg=messageConfig.getMessage(MKT_008);
			msg.setData(folio);
			msg.setDescription(result.getDescription());
			result.setData(msg).setStatus(HttpStatus.BAD_REQUEST);
			return result;
		}
		
		Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
		
		if(element.isPresent()) {
			
			getLogger().info("Se encontro la landing con folio {} en la BD",folio);
			LandingApplication landing = landingHelper.toModel(element.get());
			return new Message().setData(landing).setStatus(HttpStatus.OK);
		}
		else {
			getLogger().warn("No se encontro la landing con folio {} en la BD");
			Message msg=messageConfig.getMessage(MKT_003);
			return msg.setStatus(HttpStatus.NOT_FOUND);
		}
		
	}
	
	public Message createLanding(LandingApplication landing) {
		
		try {
			Message result=validateLandingService.validatePersonalData(landing.getPersonalData());
			Boolean ind = (Boolean)result.getData();
			if(!ind) {
				getLogger().warn("Los datos de entrada no pasaron las validaciones correspondientes");
				Message msg=messageConfig.getMessage(MKT_008);
				msg.setDescription(result.getDescription());
				result.setData(msg).setStatus(HttpStatus.BAD_REQUEST);
				return result;
			}
			
			Landing entity=landingHelper.toEntity(landing);
			Landing resultEntity=landingRepository.save(entity);
			getLogger().info("Se creo la landing con folio {}",resultEntity.getFolio());
			
			Message msg=messageConfig.getMessage(MKT_001);
			return msg.setStatus(HttpStatus.OK);
		}
		catch(Exception e) {
			
			getLogger().error("Ocurrio un error al guardar la landing {}",e);
			Message msg=messageConfig.getMessage(MKT_005);
			return msg.setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Message updateLanding(LandingApplication landing,String folio) {
		
		try {
			Message result=validateLandingService.validateFolio(folio);
			Boolean ind = (Boolean)result.getData();
			
			Message result2=validateLandingService.validatePersonalData(landing.getPersonalData());
			Boolean ind2 = (Boolean)result2.getData();
			if(!ind || !ind2) {
				
				getLogger().warn("El folio o los datos de entrada no pasaron las validaciones correspondientes");
				Message msg=messageConfig.getMessage(MKT_008);
				msg.setDescription(result.getDescription());
				result.setData(msg).setStatus(HttpStatus.BAD_REQUEST);
				return result;
			}
			
			Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
			if(element.isPresent()) {
				
				getLogger().info("Actualizando landing con folio {}",folio);
				Landing entity = element.get();
				landingHelper.toUpdateEntity(landing, entity);
				landingRepository.save(entity);
				getLogger().info("Se actualizo la landing con folio {}",folio);
				return new Message().setStatus(HttpStatus.OK);
			}
			else {
				getLogger().info("No se encontro la landing con folio {}",folio);
				return createLanding(landing);
			}
		}
		catch(Exception e) {
			
			getLogger().info("No se pudo actualizar/crear la landing {}",e);
			Message msg=messageConfig.getMessage(MKT_006);
			return msg.setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public Message deleteLanding(String folio) {
		
		try {
			Message result=validateLandingService.validateFolio(folio);
			Boolean ind = (Boolean)result.getData();
			if(!ind) {
				
				getLogger().warn("El folio no paso las validaciones correspondientes {}",folio);
				Message msg=messageConfig.getMessage(MKT_008);
				msg.setData(folio);
				msg.setDescription(result.getDescription());
				result.setData(msg).setStatus(HttpStatus.BAD_REQUEST);
				return result;
			}
			
			Optional<Landing> element=landingRepository.findById(Integer.valueOf(folio));
			if(element.isPresent()) {
				getLogger().info("Eliminado landing con folio {}",folio);
				landingRepository.deleteById(Integer.valueOf(folio));
				getLogger().info("Se elimino landing con folio {}",folio);
				Message msg=messageConfig.getMessage(MKT_004);
				return msg.setStatus(HttpStatus.OK);
			}
			else {
				getLogger().info("No se encontro landing con folio {}",folio);
				return new Message().setStatus(HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			
			getLogger().info("Ocurrio un error al eliminar la landing",e);
			Message msg=messageConfig.getMessage(MKT_007);
			return msg.setDescription(e.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
