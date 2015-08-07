
package com.search.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.*;

import com.search.common.NavigationBean;

public class DatabaseService {

    public static Connection conn;

    public DatabaseService() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/search_engine_db";
            String username = "root";
            String password = "root";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to insert a new user into database
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
      * @return
     * @throws SQLException
     */
    public boolean registerUser(String firstName, String lastName, String username, String password, String email, String contact, String interest) throws SQLException {
        boolean result = false;
        String sql = "insert into user(first_name,last_name,username,password,email,contact,interests) values(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, username);
        pstmt.setString(4, password);
        pstmt.setString(5, email);
        pstmt.setString(6, contact);
        pstmt.setString(7, interest);
        
        System.out.println(sql);
      
        if (pstmt.executeUpdate() == 1) {
            result = true;
        }
        pstmt.close();

        return result;
    }

    /**
     * Method to validate if username already exists or not.
     *
     * @param username
     * @return
     */
    public boolean validUserName(String username) {
        boolean result = false;
        Statement stmt;
        try {
            stmt = conn.createStatement();

            String strSelect = "select username from user";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                if (username.equals(rset.getString("username"))) {
                    result = true;
                    break;
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Method to check login credentials of user
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validateUser(String username, String password) {
        boolean result = false;
        Statement stmt;
        try {
            stmt = conn.createStatement();

            String strSelect = "select username,password from user";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) 
            {
                if ((username.equals(rset.getString("username"))) && (password.equals(rset.getString("password")))) {
                    result = true;
                    break;
                }
            }
            stmt.close();
        } catch (SQLException e) 
        {
        	System.out.println("validate Login Error ");
            e.printStackTrace();
        }
        System.out.println("Login Result : "+result);
        return result;
    }

    /**
     * Method to save url information into database
     *
     * @param username
     * @param url
     * @param suburl
     * @param query
     * @return
     * @throws SQLException
     */
    public boolean saveLink(String username, String url, String query) throws SQLException {
        boolean result = false;
        int hits = linkAlreadyExists(username, url);
        System.out.println("Hits for User :" + hits);
        if (hits > 0) {
            PreparedStatement pstmt = conn.prepareStatement("update navigation_history  SET  hits = ?,timestamp = ? WHERE link like ?");
            pstmt.setInt(1, ++hits);
            java.util.Date date = new java.util.Date();
            pstmt.setTimestamp(2, new Timestamp(date.getTime()));
            pstmt.setString(3, "%" + url + "%");

            if (pstmt.executeUpdate() == 1) {
                result = true;
            }
        } else {
            PreparedStatement pstmt = conn.prepareStatement("select id from user WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rset = pstmt.executeQuery();
            int userId = 0;
            while (rset.next()) {
                userId = rset.getInt("id");
            }

            PreparedStatement pstmt1 = conn.prepareStatement("insert into navigation_history(link,user_id,hits,query) values(?,?,?,?)");
            pstmt1.setString(1, url);
            pstmt1.setInt(2, userId);
            pstmt1.setInt(3, 1);
            pstmt1.setString(4, query.toLowerCase());
            if (pstmt1.executeUpdate() == 1) {
                result = true;
            }

        }
        return result;
    }

    /**
     * Method to check whether url already exists in database
     *
     * @param username
     * @param url
     * @return
     */
    private int linkAlreadyExists(String username, String url) {
        int result = 0;

        try {
            PreparedStatement pstmt = conn.prepareStatement("select link,hits from user,navigation_history where navigation_history.user_id = user.id and username = ?");
            pstmt.setString(1, username);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (rset.getString("link").contains(url)) {
                    System.out.println("Hits for url :" + rset.getInt("hits"));
                    result = rset.getInt("hits");
                    break;
                }
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getLinksForQuery(String queryString, String username) {
        List<String> personalizedUserList = new ArrayList<String>();
        try {
            PreparedStatement pStatement = conn.prepareStatement("select link from navigation_history,user where navigation_history.user_id = user.id and username = ? and query =? order by hits desc");
            pStatement.setString(1, username);
            pStatement.setString(2, queryString.toLowerCase());
            ResultSet rset = pStatement.executeQuery();
            while (rset.next()) {
                personalizedUserList.add(rset.getString("link"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return personalizedUserList;
    }

    public List<String> getBannedSites() {
        List<String> bansitesList = new ArrayList<String>();
        Statement stmt;
        try {
            stmt = conn.createStatement();

            String strSelect = "select name from ban_sites";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                bansitesList.add(rset.getString("name"));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bansitesList;
    }
     public List<String> getDomainName(String str) {
        List<String> NameList = new ArrayList<String>();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            
            String strSelect = "select keyword from " + str;
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                NameList.add(rset.getString("keyword"));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NameList;
    }


public void purgeOldData() {
	/*String purging = customize.getString("purging");
    String flag = customize.getString("flag");*/
	String purging = "8";
    String flag = "1";
        try {
            if(flag.equals("1")) {
                int purge = -(Integer.parseInt(purging));
                
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, purge);//.HOUR
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss.SSS");
                String formattedDate = sdf.format(cal.getTime());
                
                String sql = "DELETE FROM navigation_history " +"WHERE timestamp < ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,formattedDate);
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

public ArrayList<NavigationBean> navigationList(String username){
	ArrayList<NavigationBean> list = new ArrayList<NavigationBean>();
	try{
	PreparedStatement pStatement = conn.prepareStatement("select link,timestamp,hits,query from navigation_history,user where navigation_history.user_id = user.id and username = ? order by hits desc");
	pStatement.setString(1, username);
    ResultSet rset = pStatement.executeQuery();
    while(rset.next()){
    	NavigationBean bean = new NavigationBean();
    	bean.setLink(rset.getString("link"));
    	bean.setTimestamp(rset.getString("timestamp"));
    	bean.setHits(String.valueOf(rset.getInt("hits")));
    	bean.setQuery(rset.getString("query"));
    	list.add(bean);
    }
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return list;
	
}

/**
 * Method to create url information list for all users
 * @return
 */
public ArrayList<NavigationBean> userNavigationList(){
	ArrayList<NavigationBean> list = new ArrayList<NavigationBean>();
	try{
	Statement pStatement = conn.createStatement();
    ResultSet rset = pStatement.executeQuery("select username,link,timestamp,hits,query from navigation_history,user where navigation_history.user_id = user.id order by hits desc");
    while(rset.next()){
    	NavigationBean bean = new NavigationBean();
    	bean.setUser(rset.getString("username"));
    	bean.setLink(rset.getString("link"));
    	bean.setTimestamp(rset.getString("timestamp"));
    	bean.setHits(String.valueOf(rset.getInt("hits")));
    	bean.setQuery(rset.getString("query"));
    	list.add(bean);
    }
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return list;
	
}

public String getUserInterest(String username) {
	// TODO Auto-generated method stub
	Statement stmt;
	String result="";
	try {
        stmt = conn.createStatement();

        String strSelect = "select interests from user where username='"+username + "'";
        ResultSet rset = stmt.executeQuery(strSelect);
        while(rset.next()){
        	result = rset.getString(1);
        }
        stmt.close();
    } catch (SQLException e) 
    {
    	System.out.println("Error...");
        e.printStackTrace();
    }
	System.out.println("DB"+result);
	return result;
}
}    