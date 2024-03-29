package com.sap.internship.web.calculator.dto;

import java.sql.Timestamp;

public class CalculationDTO extends ResponseDTO {
	protected Double result;
	
	public CalculationDTO() {
		
	}
	
	public CalculationDTO(String expression, Double result) {
		this.expression = expression;
		this.result = result;
	}
	
	public CalculationDTO(String expression, Double result, Timestamp timestamp) {
		this.expression = expression;
		this.result = result;	
		this.timestamp = timestamp;
	}
	
	public Double getResult() {
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculationDTO other = (CalculationDTO) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	public void setResult(Double result) {
		this.result = result;
	}
}
