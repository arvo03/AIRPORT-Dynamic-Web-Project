package com.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dto.Airport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AirportSearchService {

	public Set<String> getCountryCodes() {
		System.out.println("getCountryCodes");
		List<Airport> listOfAirports = getListOfAirportsFromJSONData();
		Set<String> countryCodes = new HashSet<>();
		if (!listOfAirports.isEmpty() && listOfAirports.size() > 0) {
			for (Airport air : listOfAirports) {
				countryCodes.add(air.getCode());
			}
		}
		return countryCodes;

	}

	public List<String> getAirportCodes() {
		System.out.println("getAirportCodes");
		List<Airport> listOfAirports = getListOfAirportsFromJSONData();
		List<String> airportCodes = new ArrayList<String>();
		if (!listOfAirports.isEmpty() && listOfAirports.size() > 0) {
			for (Airport air : listOfAirports) {
				if (air.getIcao() != null && !air.getIcao().isEmpty())
					airportCodes.add(air.getIcao());
			}
		}
		return airportCodes;

	}

	public String getAirportNameByAirportCode(String parameter) {
		System.out.println("getAirportNameByAirportCode");
		List<Airport> listOfAirports = getListOfAirportsFromJSONData();
		String airportName = "";
		if (!listOfAirports.isEmpty() && listOfAirports.size() > 0) {
			for (Airport air : listOfAirports) {
				if (parameter.equalsIgnoreCase(air.getIcao())) {
					airportName = air.getName();
					break;
				}
			}
		}
		return airportName;
	}

	public String getCountryNameByCountryCode(String parameter) {
		System.out.println("getCountryNameByCountryCode");
		List<Airport> listOfAirports;
		String countryName = "";
		listOfAirports = getListOfAirportsFromJSONData();
		for (Airport air : listOfAirports) {
			if (parameter.equalsIgnoreCase(air.getCode())) {
				countryName = air.getCountry();
				break;
			}
		}
		return countryName;

	}

	public List<String> filterAirportCodeDropdown(String parameter) {
		System.out.println("filterAirportCodeDropdown");
		List<Airport> listOfAirports;
		List<String> airportCodesFiltered = new ArrayList<String>();
		listOfAirports = getListOfAirportsFromJSONData();
		for (Airport air : listOfAirports) {
			if (parameter.equalsIgnoreCase(air.getCode())) {
				if (air.getIcao() != null && !air.getIcao().isEmpty())
					airportCodesFiltered.add(air.getIcao());
			}
		}
		System.out.println("Filtered Airport Codes :" + airportCodesFiltered);
		return airportCodesFiltered;
	}

	public void searchResult(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String countryCode = request.getParameter("countryCode");
		String countryName = request.getParameter("countryName");
		String airportCode = request.getParameter("airportCode");
		String airportName = request.getParameter("airportName");
		if (airportCode != null && "SELECT".equalsIgnoreCase(airportCode))
			airportCode = null;
		if (countryCode != null && "SELECT".equalsIgnoreCase(countryCode))
			countryCode = null;
		boolean considerCountryCode = countryCode != null && !countryCode.isEmpty() ? true : false;
		boolean considerCountryName = countryName != null && !countryName.isEmpty() ? true : false;
		boolean considerAirportCode = airportCode != null && !airportCode.isEmpty() ? true : false;
		boolean considerAirportName = airportName != null && !airportName.isEmpty() ? true : false;
		System.out.println("considerCountryCode : " + considerCountryCode + ", considerCountryName : "
				+ considerCountryName + ", considerAirportCode : " + considerAirportCode + ", considerAirportName : "
				+ considerAirportName);

		System.out.println("countryCode : " + countryCode + ", countryName : " + countryName + ", airportCode : "
				+ airportCode + ", airportName : " + airportName);
		List<Airport> listOfAirports;
		List<Airport> listOfSearchedAirports = new ArrayList<>();
		listOfAirports = getListOfAirportsFromJSONData();
		System.out.println("All Airport List Size " + listOfAirports.size());
		for (Airport airport : listOfAirports) {
			if (considerCountryCode) {
				if (!(countryCode.equalsIgnoreCase(airport.getCode()))) {
					continue;
				}
			}
			if (considerCountryName) {
				if (!(countryName.equalsIgnoreCase(airport.getCountry()))) {
					continue;
				}
			}
			if (considerAirportCode) {
				if (!(airportCode.equalsIgnoreCase(airport.getIcao()))) {
					continue;
				}
			}
			if (considerAirportName) {
				if (!(airportName.equalsIgnoreCase(airport.getName()))) {
					continue;
				}
			}
			listOfSearchedAirports.add(airport);
		}

		if (considerCountryCode) {
			request.setAttribute("SEARCHED_COUNTRY_CODE", countryCode);
		}
		if (considerCountryName) {
			request.setAttribute("SEARCHED_COUNTRY_NAME", countryName);
		}
		if (considerAirportCode) {
			request.setAttribute("SEARCHED_AIRPORT_CODE", airportCode);
		}
		if (considerAirportName) {
			request.setAttribute("SEARCHED_AIRPORT_NAME", airportName);
		}
		if (listOfSearchedAirports != null && listOfSearchedAirports.size() > 0) {
			request.setAttribute("SEARCHED_AIRPORT_LIST", listOfSearchedAirports);
			request.setAttribute("SEARCHED_AIRPORT_RESPONSE", "Y");
		}

		RequestDispatcher disp = request.getRequestDispatcher("welcom.jsp");
		try {
			disp.forward(request, response);
			System.out.println("redirecting back to JSP...");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private List<Airport> getListOfAirportsFromJSONData() {
		List<Airport> listOfAirports = new ArrayList<>();
		JSONParser parser = new JSONParser();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource("airport.json");
			File file = new File(resource.toURI());
			Reader reader = new FileReader(file);
			JSONArray airportDetails = (JSONArray) parser.parse(reader);
			Gson gson = new GsonBuilder().create();
			Type listType = new TypeToken<List<Airport>>() {
			}.getType();
			listOfAirports = gson.fromJson(String.valueOf(airportDetails), listType);
//			System.out.println(listOfAirports);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfAirports;
	}

}
