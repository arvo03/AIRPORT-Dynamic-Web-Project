package com.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class LoginDao {

	public boolean validate(HttpServletRequest request) throws ClassNotFoundException {
		boolean status = false;
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pass");
		System.out.println("uname "+uname);
		JSONParser  f = new JSONParser();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
	        URL resource = classLoader.getResource("user.json");
			File file = new File(resource.toURI());
			Reader reader = new FileReader(file);
			
			JSONObject ob = (JSONObject) f.parse(reader);
			System.out.println("username "+ob.get("username"));
			if(uname.equalsIgnoreCase(ob.get("username").toString()) && pass.equalsIgnoreCase(ob.get("password").toString())){
				HttpSession activeSession = request.getSession();
				activeSession.setAttribute("username", uname);
				activeSession.setAttribute("fullname", ""+ob.get("fname")+" "+ob.get("lname"));
				status=true;
				System.out.println("NAME "+ob.get("fname")+ob.get("lname"));
				request.setAttribute("username", ""+ob.get("fname")+" "+ob.get("lname"));
			}

			
			System.out.println(ob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}

}
