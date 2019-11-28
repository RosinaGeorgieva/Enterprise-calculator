package com.sap.internship.web.po;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.database.Calculation;
import com.sap.internship.web.database.CalculationsDAO;
import com.sap.internship.web.security.VcapServices;

@Component
public class DatabasePO {
	
	@Autowired
	CalculationsDAO calculationsDAO;
	
	public void deleteAll() {
		calculationsDAO.deleteAll();
	}
	
	public CalculationDTO insert(CalculationDTO calculation) {
		calculationsDAO.insert(new Calculation( currentUser(), statusCode(), calculation.getExpression(),  calculation.getResult(), timestamp()));
		return calculation;
	}
	
	public HashSet<CalculationDTO> getHistory() {
		return new HashSet<CalculationDTO>(convert(calculationsDAO.getHistory(currentUser())));
	}
	
	private Integer statusCode() {
		return new Integer(200);
	}
	
	private String currentUser() {
		return new VcapServices().getClientId();
	}
	
	private Timestamp timestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	private List<CalculationDTO> convert(List<Calculation> calculationsTable) {
		List<CalculationDTO> calculationResponses = new ArrayList<CalculationDTO>();
		for (Calculation calculation : calculationsTable) {
			calculationResponses.add(new CalculationDTO(calculation.getExpression(), calculation.getResult(),
					calculation.getTimestamp()));
		}
		return calculationResponses;
	}
}
