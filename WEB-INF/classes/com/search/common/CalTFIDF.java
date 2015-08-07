package com.search.common;

import java.util.List;


public class CalTFIDF {

    /**
     * Calculate the TF Value for the document. . .
     *
     * @param totalterms TotalTerms of the document under processing.
     * @param termToCheck
     * @return TF value of the document.
     */
    public double tfCalculator(List<String> totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.size();
    }

    /**
     * Calculate the IDF of the term.
     * @return IDF value of the document.
     */
    public double idfCalculator() {
        double count = 1;
        
        return count;
    }
}
