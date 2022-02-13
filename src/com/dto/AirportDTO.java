package com.dto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AirportDTO implements Serializable {
	/**
	 * 
	 */

	public void init() {
		if (this.getAirportList() != null && !this.getAirportList().isEmpty()) {
			System.out.println("initiating AirportDataPoint");
			JSONParser parser = new JSONParser();
			try {
				Reader reader = new FileReader("D:\\airport.json");
				JSONArray airportDetails = (JSONArray) parser.parse(reader);
				Gson gson = new GsonBuilder().create();
				Type listType = new TypeToken<List<Airport>>() {
				}.getType();
				this.airportList = gson.fromJson(String.valueOf(airportDetails), listType);
				System.out.println(this.airportList);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static final long serialVersionUID = 1L;
	private String username;
	private List<Airport> airportList;
	private List<String> airportCodes;
	private String password;

	public List<Airport> getAirportList() {
		return airportList;
	}

	public void setAirportList(List<Airport> airportList) {
		this.airportList = airportList;
	}

	public List<String> getAirportCodes() {
		init();
		List<String> airportCodes = new ArrayList<String>();
		for(Airport air : this.airportList) {
			airportCodes.add(air.getCode());
		}
		return airportCodes;
	}

	public void setAirportCodes(List<String> airportCodes) {
		this.airportCodes = airportCodes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}