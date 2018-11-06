package com.elifsat.searchengine.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomSearchResponseDTO implements Serializable {

	private static final long serialVersionUID = 4067765621219902691L;

	private String title;
	private String htmlTitle;
	private String link;
	private String displayLink;
	private String snippet;
	private String htmlSnippet;
	private String formattedUrl;
	private String htmlFormattedUrl;
	private long ratio;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlTitle() {
		return htmlTitle;
	}

	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDisplayLink() {
		return displayLink;
	}

	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getHtmlSnippet() {
		return htmlSnippet;
	}

	public void setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}

	public String getFormattedUrl() {
		return formattedUrl;
	}

	public void setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
	}

	public String getHtmlFormattedUrl() {
		return htmlFormattedUrl;
	}

	public void setHtmlFormattedUrl(String htmlFormattedUrl) {
		this.htmlFormattedUrl = htmlFormattedUrl;
	}
	
	public long getRatio() {
		return ratio;
	}

	public void setRatio(long l) {
		this.ratio = l;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("title:").append(title).append("\n")
				.append("htmlTitle:").append(htmlTitle).append("\n")
				.append("link:").append(link).append("\n")
				.append("displayLink:").append(displayLink).append("\n")
				.append("htmlSnippet:").append(htmlSnippet).append("\n")
				.append("formattedUrl:").append(formattedUrl).append("\n")
				.append("ratio:").append(ratio).append("\n")
				.append("htmlFormattedUrl:").append(htmlFormattedUrl).append("\n\n\n");
		
		return builder.toString();
	}

}
