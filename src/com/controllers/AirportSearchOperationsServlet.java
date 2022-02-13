package com.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dto.AirportDTO;
import com.services.AirportSearchService;

/**
 * Servlet implementation class AirportSearchOperationsServlet
 */
@WebServlet("/AirportSearchOperationsServlet")
public class AirportSearchOperationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AirportSearchService service;
	AirportDTO airportDTO;

	public void init() {
		System.out.println("initiating AirportSearchOperationsServlet");
		service = new AirportSearchService();
		airportDTO = new AirportDTO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("getAirportNameByAirportCode") != null
				&& !request.getParameter("getAirportNameByAirportCode").isEmpty()) {
			String airportName = service
					.getAirportNameByAirportCode(request.getParameter("getAirportNameByAirportCode"));
			response.getWriter().write("<input type=\"text\" name=\"airportName\" value='"+airportName+"' readonly>");
		} else if (request.getParameter("getCountryNameByCountryCode") != null
				&& !request.getParameter("getCountryNameByCountryCode").isEmpty()) {
			String countryName = service
					.getCountryNameByCountryCode(request.getParameter("getCountryNameByCountryCode"));
			response.getWriter().write("<input type=\"text\" name=\"countryName\" value='"+countryName+"' readonly>");
		} else if (request.getParameter("filterAirportCodeDropdown") != null
				&& !request.getParameter("filterAirportCodeDropdown").isEmpty()) {
			List<String> airportCodesFiltered = service
					.filterAirportCodeDropdown(request.getParameter("filterAirportCodeDropdown"));
			response.getWriter().write("<option>SELECT</option>");
			if (airportCodesFiltered != null && !airportCodesFiltered.isEmpty()) {
				for (String airportCode : airportCodesFiltered) {
					response.getWriter().write("<option value='" + airportCode + "'>" + airportCode + "</option>");
				}
			}
		} else if (request.getParameter("getAllAirportCodes") != null
				&& !request.getParameter("getAllAirportCodes").isEmpty()) {
			List<String> airportCodes = service.getAirportCodes();
			if (airportCodes != null && !airportCodes.isEmpty()) {
				response.getWriter().write("<option>SELECT</option>");
				for (String airportCode : airportCodes) {
					response.getWriter().write("<option value='" + airportCode + "'>" + airportCode + "</option>");
				}
			}
		}
		else if(request.getParameter("searchSubmit")!=null) {
			System.out.println("submited");
			service.searchResult(request,response);
		}

	}

}
