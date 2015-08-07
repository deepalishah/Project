
package com.search.common;


public class CosineSimilarity {
    
    public double cosineSimilarity(double tfValue, double idfValue) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        dotProduct = tfValue * idfValue;
        magnitude1 = Math.sqrt(tfValue);//sqrt(a^2)
        magnitude2 = Math.sqrt(idfValue);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }
}
