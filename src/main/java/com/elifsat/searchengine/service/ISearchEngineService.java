package com.elifsat.searchengine.service;

import java.util.List;

import com.elifsat.searchengine.dto.CustomSearchResponseDTO;

public interface ISearchEngineService {

	public List<CustomSearchResponseDTO> seacrh(String key) throws Exception;
	
}
