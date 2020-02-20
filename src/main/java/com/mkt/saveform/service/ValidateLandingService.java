package com.mkt.saveform.service;

import static com.mkt.core.constants.ConstantsRegularExpression.REGEX_NUMERICS;
import static com.mkt.core.constants.ConstantsRegularExpression.REGEX_NAME;
import static com.mkt.core.constants.ConstantsRegularExpression.REGEX_PHONES;
import static com.mkt.core.constants.ConstantsRegularExpression.REGEX_EMAIL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkt.core.base.BaseRegularExpression;
import com.mkt.core.base.BaseService;
import com.mkt.core.model.Message;
import com.mkt.core.model.PersonalData;
import com.mkt.core.validators.RegularExpressionRule;

@Service
public class ValidateLandingService extends BaseService{
	
	@Autowired
	private RegularExpressionRule regularExpressionRule;
	
	private Map<FIELDS_TO_VALIDATE,BaseRegularExpression> patterns;
	
	private enum FIELDS_TO_VALIDATE{
		FOLIO,FIRST_NAME,SECOND_NAME,LAST_NAME,SECOND_LAST_NAME,HOMEPHONE,CELLPHONE,EMAIL;
	}
	
	@PostConstruct
	public void init() {
		
		patterns = new HashMap<>();
		
		BaseRegularExpression exp = new BaseRegularExpression();
		exp.setExpression(REGEX_NUMERICS);
		exp.setMaxLength(10);
		exp.setMinLength(1);
		exp.setOptional(false);
		patterns.put(FIELDS_TO_VALIDATE.FOLIO, exp);
		
		BaseRegularExpression exp2 = new BaseRegularExpression();
		exp2.setExpression(REGEX_NAME);
		exp2.setMaxLength(50);
		exp2.setMinLength(3);
		exp2.setOptional(false);
		patterns.put(FIELDS_TO_VALIDATE.FIRST_NAME, exp2);
		
		BaseRegularExpression exp3 = new BaseRegularExpression();
		exp3.setExpression(REGEX_NAME);
		exp3.setMaxLength(50);
		exp3.setMinLength(3);
		exp3.setOptional(true);
		patterns.put(FIELDS_TO_VALIDATE.SECOND_NAME, exp3);
		
		BaseRegularExpression exp4 = new BaseRegularExpression();
		exp4.setExpression(REGEX_NAME);
		exp4.setMaxLength(50);
		exp4.setMinLength(3);
		exp4.setOptional(false);
		patterns.put(FIELDS_TO_VALIDATE.LAST_NAME, exp4);
		
		BaseRegularExpression exp5 = new BaseRegularExpression();
		exp5.setExpression(REGEX_NAME);
		exp5.setMaxLength(50);
		exp5.setMinLength(3);
		exp5.setOptional(true);
		patterns.put(FIELDS_TO_VALIDATE.SECOND_LAST_NAME, exp5);
		
		BaseRegularExpression exp6 = new BaseRegularExpression();
		exp6.setExpression(REGEX_PHONES);
		exp6.setMaxLength(10);
		exp6.setMinLength(10);
		exp6.setOptional(true);
		patterns.put(FIELDS_TO_VALIDATE.HOMEPHONE, exp6);
		
		BaseRegularExpression exp7 = new BaseRegularExpression();
		exp7.setExpression(REGEX_PHONES);
		exp7.setMaxLength(10);
		exp7.setMinLength(10);
		exp7.setOptional(false);
		patterns.put(FIELDS_TO_VALIDATE.CELLPHONE, exp7);
		
		BaseRegularExpression exp8 = new BaseRegularExpression();
		exp8.setExpression(REGEX_EMAIL);
		exp8.setMaxLength(50);
		exp8.setMinLength(5);
		exp8.setOptional(false);
		patterns.put(FIELDS_TO_VALIDATE.EMAIL, exp8);
		
		
	}
	
	public Message validateFolio(String folio) {
		
		getLogger().info("Validando folio");
		return regularExpressionRule.evaluate(folio,patterns.get(FIELDS_TO_VALIDATE.FOLIO));
		
	}
	
	public Message validateFirstName(String name) {
		
		getLogger().info("Validando primer nombre");
		return regularExpressionRule.evaluate(name,patterns.get(FIELDS_TO_VALIDATE.FIRST_NAME));
	}
	
	public Message validateSecondName(String secondName) {
		
		getLogger().info("Validando segundo nombre");
		return regularExpressionRule.evaluate(secondName,patterns.get(FIELDS_TO_VALIDATE.SECOND_NAME));
	}

	public Message validateLastName(String lastName) {
	
		getLogger().info("Validando apellido paterno");
		return regularExpressionRule.evaluate(lastName,patterns.get(FIELDS_TO_VALIDATE.LAST_NAME));
	}

	public Message validateSecondLastName(String secondLastName) {
	
		getLogger().info("Validando apellido materno");
		return regularExpressionRule.evaluate(secondLastName,patterns.get(FIELDS_TO_VALIDATE.SECOND_LAST_NAME));
	}

	public Message validateHomePhone(String homephone) {
		
		getLogger().info("Validando telefono de casa");
		return regularExpressionRule.evaluate(homephone,patterns.get(FIELDS_TO_VALIDATE.HOMEPHONE));
	}
	
	public Message validateCellPhone(String cellphone) {
		
		getLogger().info("Validando celular");
		return regularExpressionRule.evaluate(cellphone,patterns.get(FIELDS_TO_VALIDATE.CELLPHONE));
	}
	
	public Message validateEmail(String email) {
		
		getLogger().info("Validando correo");
		return regularExpressionRule.evaluate(email,patterns.get(FIELDS_TO_VALIDATE.EMAIL));
	}
	
	public Message validatePersonalData(PersonalData personal) {
		
		getLogger().info("Validando bloque de datos personales");
		if(personal==null) {
			return new Message().setData(Boolean.FALSE).setDescription("El request no tiene los campos necesarios");
		}
		
		List<Message> errors = new ArrayList<>();
		
		Message field1 = validateFirstName(personal.getFirstName());
		Message field2 = validateSecondName(personal.getSecondName());
		Message field3 = validateLastName(personal.getLastName());
		Message field4 = validateSecondLastName(personal.getSecondLastName());
		Message field5 = validateEmail(personal.getEmail());
		Message field6 = validateHomePhone(personal.getHomePhone());
		Message field7 = validateCellPhone(personal.getCellPhone());
		
		errors.add(field1);
		errors.add(field2);
		errors.add(field3);
		errors.add(field4);
		errors.add(field5);
		errors.add(field6);
		errors.add(field7);
		
		Optional<Message> result=errors.stream().filter(error -> Boolean.FALSE.equals(((Boolean)error.getData()))).findFirst();
		
		return result.orElse(new Message().setData(Boolean.TRUE));
		
	}

}
