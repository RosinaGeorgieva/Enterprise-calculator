package com.sap.internship.web.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculationsDAO {
	private CalculationsRepository calculationsRepository;
	
	@Autowired
	public CalculationsDAO(CalculationsRepository calculationsRepository) {
		this.calculationsRepository = calculationsRepository;
	}
	
	public Calculation insert(Calculation calculation) {
		return calculationsRepository.save(calculation);
	}	
	
	public List<Calculation> getHistory(String user) {
		return calculationsRepository.findByUsername(user);
	}
	
	public void deleteAll() {
		calculationsRepository.deleteAll();
	}
}
