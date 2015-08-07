/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search.common;

import com.search.services.DatabaseService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class VectorSpaceModel {

	public List<Result> getResultsAccoringToVectorCalculation(String queryString, String username, List<Result> googleResultList) {
        List<Result> resultList = new ArrayList<Result>();
        Map<String, Result> twoCountResultMap = new TreeMap<String, Result>();
        Map<String, Result> oneCountResultMap = new TreeMap<String, Result>();
        List<Result> twoCountList = new ArrayList<Result>();
        List<Result> oneCountList = new ArrayList<Result>();
        List<String> personalizedUserList = new ArrayList<String>();
        List<String> personalizedUserResultSet = new ArrayList<String>();
        List<Result> personalizedResultList = new ArrayList<Result>();
        Set<Result> personalizedResultSet = new HashSet<Result>();
        List<Result> finalSortedPersonalizedList = new ArrayList<Result>();
        List<String> dmozResultList = new ArrayList<String>();
        
        String interest = "";
        
        try {
        	for (int i = 0; i < googleResultList.size(); i++) {
               
        		oneCountResultMap.put(googleResultList.get(i).getUrl(), googleResultList.get(i));
        		
            }
            DatabaseService dbm = new DatabaseService();
            personalizedUserList = dbm.getLinksForQuery(queryString, username);

            // Add the two count result to final list. . .
            for (Map.Entry<String, Result> entry : twoCountResultMap.entrySet()) {
                if (personalizedUserList.contains(entry.getValue().getUrl())) {
                    personalizedUserResultSet.add(entry.getValue().getUrl());
                    personalizedResultSet.add(entry.getValue());
                } else {
                    twoCountList.add(entry.getValue());
                }
            }
            resultList.addAll(getDocumentsSimilarity(queryString.toLowerCase(), twoCountList));


            // Add the one count result to final list. . .
            for (Map.Entry<String, Result> entry : oneCountResultMap.entrySet()) {
                if (personalizedUserList.contains(entry.getValue().getUrl())) {
                    if (!personalizedUserResultSet.contains(entry.getValue().getUrl())) {
                        personalizedResultSet.add(entry.getValue());
                    }
                } else {
                    oneCountList.add(entry.getValue());
                }
            }
            
            resultList.addAll(getDocumentsSimilarity(queryString.toLowerCase(), oneCountList));
            resultList = removeRedudantResults(resultList);

            // Sort the personalized results as per the hits.
            for (String url : personalizedUserList) {
                for (Result result : personalizedResultSet) {
                    if(url.equals(result.getUrl())) {
                        finalSortedPersonalizedList.add(result);
                    }
                }
            }

            personalizedResultList.addAll(finalSortedPersonalizedList);
            
            interest = dbm.getUserInterest(username);
            dmozResultList = getDMozResults(interest, queryString);
            for(Result res : resultList) {
            	String url = res.getUrl();
            	String finalUrl = processLink(url);
            	if(dmozResultList.contains(finalUrl)) {
            		personalizedResultList.add(res);
            	}
            }
            for(Result res : resultList) {
            	if(!personalizedResultList.contains(res)) {
            		personalizedResultList.add(res);
            	}
            }
            personalizedResultList.addAll(resultList);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
        
        return personalizedResultList;
    }

    private String processLink(String resultLink) {
		String[] splits = resultLink.split("/");
		if(splits.length > 2) {
 		String finalStr = "http://"+splits[2];
 		return finalStr;
		}
		return null;
	}

    // Algorithm
    // 1. Tokenize the document.
    // 2. Calculate the TF Value.
    // 3. Calculate the IDF value.
    // 4. Calculate the dot product of the TF.IDF
    // 5. Calculate the cosine of document using TF and IDF
    public List<Result> getDocumentsSimilarity(String queryString, List<Result> resultList) {
        try {
            VectorSpaceModel vectorSpaceModel = new VectorSpaceModel();
            CalTFIDF calTFIDF = new CalTFIDF();
            CosineSimilarity cosineSimilarity = new CosineSimilarity();
            List<Double> cosineSimilarityList = new ArrayList<Double>();
            for (Result result : resultList) {
                double tfValue = 0.0;
                double idfValue = 0.0;
                double cosineValue = 0.0;
                List<String> allTermsOfDocument = vectorSpaceModel.getTokensOfDocument(result.getDescription().toLowerCase());
                tfValue = calTFIDF.tfCalculator(allTermsOfDocument, queryString);
                idfValue = calTFIDF.idfCalculator();
                cosineValue = cosineSimilarity.cosineSimilarity(tfValue, idfValue);

                cosineSimilarityList.add(cosineValue);
            }


            Double[] cosineSimilarityArray = cosineSimilarityList.toArray(new Double[cosineSimilarityList.size()]);
            Result[] resultArray = resultList.toArray(new Result[resultList.size()]);
            double swapCosine;
            Result swapResult;
            for (int i = 0; i < (cosineSimilarityArray.length - 1); i++) {
                for (int j = 0; j < cosineSimilarityArray.length - i - 1; j++) {
                    if (cosineSimilarityArray[j] > cosineSimilarityArray[j + 1]) {
                        swapCosine = cosineSimilarityArray[j];
                        cosineSimilarityArray[j] = cosineSimilarityArray[j + 1];
                        cosineSimilarityArray[j + 1] = swapCosine;

                        swapResult = resultArray[j];
                        resultArray[j] = resultArray[j + 1];
                        resultArray[j + 1] = swapResult;
                    }
                }
            }

            resultList = Arrays.asList(resultArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public List<String> getTokensOfDocument(String docContent) {
        List<String> returnList = new ArrayList<String>();
        List allTerms = new ArrayList(); //to hold all terms

        try {
            docContent = docContent.replaceAll("<[^>]*>", "");
            String[] tokenizedTerms = docContent.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
            for (String term : tokenizedTerms) {
                if (!allTerms.contains(term)) {  //avoid duplicate entry
                    allTerms.add(term);
                }
            }
            returnList.addAll(Arrays.asList(tokenizedTerms));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;

    }

    public List<Result> removeRedudantResults(List<Result> resultList) {
        List<Result> returnedList = new ArrayList<Result>();
        List<String> reUrlList = new ArrayList<String>();
        try {
            for (Result result : resultList) {
                if (!reUrlList.contains(result.getUrl())) {
                    reUrlList.add(result.getUrl());
                    returnedList.add(result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnedList;
    }
    
    public static ArrayList<String> getDMozResults(String interest, String query) {
        ArrayList<String> list = new ArrayList<String>();
        String[] interests = interest.split(" ");
        System.out.println("total inrests : " + interests.length);
        for(int i=0;i<interests.length;i++) {
            String link = "http://www.dmoz.org/search?q="+query.replaceAll(" ", "+") +"&cat=" + interests[i].trim() + "&start=0";
            try {
            	System.out.println(link);
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
                }
                reader.close();
                Document document = Jsoup.parse(builder.toString());
                Elements elements = document.select("ol.site>li>a");
                System.out.println("---------- DMOZ Results -----------------");
                if(elements.size() == 0) {
                	elements = document.select("ol.dir>li>a");
                }
                for(Element element : elements) {
                	String linkUrl = element.attr("href");
                	System.out.println(linkUrl);
                    list.add(linkUrl);
                }
//                System.out.println("---------- DMOZ Results -----------------");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return list;
    }
}
