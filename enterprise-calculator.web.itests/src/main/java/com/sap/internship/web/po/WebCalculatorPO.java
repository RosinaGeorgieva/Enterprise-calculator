package com.sap.internship.web.po;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;

import com.sap.internship.web.calculator.dto.CalculationDTO;
import com.sap.internship.web.exception.BadRequestException;
import com.sap.internship.web.exception.RESTGenericException;
import com.sap.internship.web.exception.UnauthenticatedUserException;
import com.sap.internship.web.service.Environment;
import com.sap.internship.web.service.IWebCalculatorService;
import com.sap.internship.web.service.WebCalculatorLifecycle;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebCalculatorPO {
	private String url;
	private String token;
	private Retrofit retrofit;
	private IWebCalculatorService service;

	public WebCalculatorPO(String url, String token) {
		this.url = url;
		this.token = token;
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
				.client(builder.build()).build();
		this.service = retrofit.create(IWebCalculatorService.class);
	}
	
	public WebCalculatorPO(Environment environment, String token) {
		this(WebCalculatorLifecycle.getURL(environment.getPort()), token);
	}
	
	public WebCalculatorPO(WebCalculatorPO webCalculatorPO) {
		this.url = webCalculatorPO.url;
		this.retrofit = webCalculatorPO.retrofit;
	}

	public boolean serviceIsAvailable() {
		try {
			URL myURL = new URL(url);
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public String authHeader(String token) {
		return "Bearer"+token;
	}

	public CalculationDTO evaluate(String expression) throws Exception {
		Call<CalculationDTO> call = service.requestCalculation(expression, authHeader(token));
		Response<CalculationDTO> response = call.execute();
		if(response.code() == HttpURLConnection.HTTP_OK) {
			return response.body();
		} if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new BadRequestException(response.message());
		} if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
			throw new UnauthenticatedUserException(response.message());
		} throw new RESTGenericException(String.valueOf(response.code()));
	}
	
	public HashSet<CalculationDTO> getHistory() throws IOException {
		Call<List<CalculationDTO>> call = service.requestHistory(authHeader(token));
		Response<List<CalculationDTO>> response = call.execute();
		return new HashSet<CalculationDTO>(response.body());
	}
}
