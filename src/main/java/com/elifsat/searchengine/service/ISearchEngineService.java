package com.elifsat.searchengine.service;

import java.util.List;

import com.elifsat.searchengine.dto.CustomSearchResponseDTO;

public interface ISearchEngineService {

	public List<CustomSearchResponseDTO> seacrh(String key) throws Exception;
	
	/**
	 * 
	 * @param currentStr arama yapılan string
	 * @param targetStr arama sonucunda google custom search apiden donen deger
	 * @return benzerlik oranı dondurulur 
	 */
	public double calculateSimilarityRatio(String currentStr, String targetStr);
	
}
