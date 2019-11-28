package com.sap.internship.web.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationsRepository extends JpaRepository<Calculation, String> {
	public List<Calculation> findByUsername(String username);
}
