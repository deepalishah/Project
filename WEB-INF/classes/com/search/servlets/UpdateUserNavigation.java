

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.servlets;

import com.search.services.DatabaseService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UpdateUserNavigation extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            String url = request.getParameter("url");
            String queryString = request.getParameter("queryString");

            System.out.println("\nUSERNAME :" + username);
            System.out.println("Query :" + queryString);
            System.out.println("Url :" + url);

            DatabaseService dbm = new DatabaseService();
            dbm.saveLink(username, url, queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
