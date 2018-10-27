package com.elifsat.searchengine.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elifsat.searchengine.dto.CustomSearchResponseDTO;

@Service("seacrhEngineService")
public class SearchEngineServiceImpl implements ISearchEngineService {
	
	private static final Logger logger = LogManager.getLogger(SearchEngineServiceImpl.class);

	@Value("${authentication.api.key}")
	private String apiKey;
	
	@Value("${authentication.custom.search.engineKey}")
	private String customSearchEngineKey;
	
	@Value("${authentication.search.url}")
	private String searchURL;
	
	@Override
	public List<CustomSearchResponseDTO> seacrh(String key) throws Exception {
		logger.info("search method begins. Key:" + key);
		 
		List<CustomSearchResponseDTO> dtos = new ArrayList<CustomSearchResponseDTO>();
		
		try {
			String searchKey = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

			String url = buildSearchString(searchKey, 1, 10);
			
			String result = callGoogleCustomSearchApi(url);
			
			logger.info(result);
			
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		
		logger.info("search method complated.");
		return dtos;
	}

	private String callGoogleCustomSearchApi(String pUrl) {
		try {
			URL url = new URL(pUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String buildSearchString(String searchString, int start, int numOfResults) {
		String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey + "&q=";

		String newSearchString = searchString.replace(" ", "%20");

		toSearch += newSearchString;

		toSearch += "&alt=json";

		toSearch += "&start=" + start;

		toSearch += "&num=" + numOfResults;

		logger.info("Seacrh URL: " + toSearch);
		
		return toSearch;
	}

}
