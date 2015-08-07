/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.servlets;

import com.search.services.DatabaseService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RegistrationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        
        String temp = "";
        String interest="";
        
        String select[] = request.getParameterValues("id"); 
        if (select != null && select.length != 0) {
        	System.out.println("You have selected: ");
        	for (int i = 0; i < select.length; i++) {
        		temp = temp+" "+select[i];	
        	}
        	System.out.println(temp);
        	interest = temp.trim();
        }
           
        DatabaseService dbm = new DatabaseService();
        HttpSession session = request.getSession();
        if (!dbm.validUserName(username))
        {
            try
            {
                
            	dbm.registerUser(firstName, lastName,username, password, email,contact,interest);
             
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            System.out.println("\nNEW USER : "+username);
            System.out.println("Registration Successfully done....!!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else
        {
            session.setAttribute("msg", "Username already exists!!");
                 
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
