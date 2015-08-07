package com.search.common;
/**
 * Bean class to hold values required for navigation information for table
 * @author 
 *
 */
public class NavigationBean {
	private String link;
	private String timestamp;
	private String hits;
	private String query;
	private String user;
	
	
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	@Override
	public String toString() {
		return "NavigationBean [hits=" + hits + ", link=" + link + ", query="
				+ query + ", timestamp=" + timestamp + "]";
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
 
}
