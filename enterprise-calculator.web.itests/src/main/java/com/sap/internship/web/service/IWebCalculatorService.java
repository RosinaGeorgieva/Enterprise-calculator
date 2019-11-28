package com.sap.internship.web.service;


import java.util.List;

import com.sap.internship.web.calculator.dto.CalculationDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IWebCalculatorService {
    @GET("/calculator/api/v1/evaluate")
    public Call<CalculationDTO> requestCalculation(@Query("expression") String expression, @Header("Authorization") String header);
    
    @GET("/calculator/api/v1/history")
    public Call<List<CalculationDTO>> requestHistory(@Header("Authorization") String header);
}
