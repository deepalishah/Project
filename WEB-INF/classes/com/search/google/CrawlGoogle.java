/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.google;

import com.google.gson.Gson;
import com.search.common.Result;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CrawlGoogle {
    
    public static List<Result> getGoogleResults(String queryString,int n) throws IOException {
        List<Result> resultList = new ArrayList<Result>();
        
        for (int i = 0; i < n; i = i + 4) {
            try {
                String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start=" + i + "&q=";
                String query = queryString;
                String charset = "UTF-8";
                
                URL url = new URL(address + URLEncoder.encode(query, charset));
                Reader reader = new InputStreamReader(url.openStream(), charset);
                GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
                
                if (results.getResponseData().getResults() != null && results.getResponseData().getResults().size() > 0) {
                    int total = results.getResponseData().getResults().size();

                    // Show title and URL of each results
                    for (int j = 0; j <= total - 1; j++) {
                        String urlLink = results.getResponseData().getResults().get(j).getUrl();
                        String title = results.getResponseData().getResults().get(j).getTitle();
                        String description = results.getResponseData().getResults().get(j).getContent();
                        resultList.add(new Result(urlLink, title, description));
                    }
                }
                
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        
        return resultList;
    }
}
