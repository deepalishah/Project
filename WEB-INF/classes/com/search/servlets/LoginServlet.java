/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.servlets;

import com.search.services.DatabaseService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DatabaseService dbm = new DatabaseService();
        if (dbm.validateUser(username, password)) 
        {
        	//System.out.println("Before Purging data");
            // dbm.purgeOldData();
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            System.out.println("\nUSER : " +username);
            System.out.println("Login : True");
            response.sendRedirect("landingPage.jsp");
        } else {
            HttpSession session = request.getSession();
             session.setAttribute("msg", "Incorrect username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
