
package com.search.common;


public class Result {
    
    private String url;
    private String description;
    private String title;

    public Result(String url,String title,String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Result{" + "url=" + url + ", description=" + description + ", title=" + title + '}';
    }
}
