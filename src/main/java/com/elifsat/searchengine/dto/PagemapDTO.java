package com.elifsat.searchengine.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagemapDTO {

	private List<CSEImageDTO> cse_image;

	public List<CSEImageDTO> getCse_image() {
		if (CollectionUtils.isEmpty(cse_image)) {
			cse_image = new ArrayList<CSEImageDTO>();
		}
		return cse_image;
	}

	public void setCse_image(List<CSEImageDTO> cse_image) {
		this.cse_image = cse_image;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (!CollectionUtils.isEmpty(cse_image)) {
			for (CSEImageDTO cseImageDTO : cse_image) {
				builder.append(cseImageDTO.toString()).append("\n");
			}
		}
		
		
		return builder.toString();
	}

}
