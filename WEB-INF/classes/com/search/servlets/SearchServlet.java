/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.servlets;


import com.search.common.Result;
import com.search.common.VectorSpaceModel;
import com.search.google.CrawlGoogle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Tribbianis
 */
public class SearchServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String queryString = "";
    String context="";
    List<Result> resultList;
    HttpSession session;
    List<Result> googleSearchResults;
   
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        resultList = new ArrayList<Result>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        queryString = request.getParameter("queryString");
    
        googleSearchResults = new ArrayList<Result>();
        googleSearchResults = CrawlGoogle.getGoogleResults(queryString, 20);
        
        VectorSpaceModel vectorSpaceModel = new VectorSpaceModel();
        resultList = vectorSpaceModel.getResultsAccoringToVectorCalculation(queryString, username, googleSearchResults);
        
        System.out.println("QueryString :" + queryString);
        System.out.println("Google Results :" + googleSearchResults.size());
           
        request.setAttribute("resultList", resultList);
        request.setAttribute("queryString", queryString);
        request.setAttribute("username", username);
          
        request.getRequestDispatcher("/results.jsp").forward(request, response);
       
        googleSearchResults.clear();   
        resultList.clear();
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }
}
