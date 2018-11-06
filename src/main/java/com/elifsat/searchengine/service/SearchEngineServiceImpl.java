package com.elifsat.searchengine.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.elifsat.searchengine.dto.CustomSearchResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("seacrhEngineService")
public class SearchEngineServiceImpl implements ISearchEngineService {

	private static final Logger logger = LogManager.getLogger(SearchEngineServiceImpl.class);

	/**
	 * Google Custom Search api cagrisinda kullanilan api key application.properties
	 * dosyasından alinir
	 */
	@Value("${authentication.api.key}")
	private String apiKey;

	@Value("${authentication.custom.search.engineKey}")
	private String customSearchEngineKey;

	@Value("${authentication.search.url}")
	private String searchURL;

	@Override
	public List<CustomSearchResponseDTO> seacrh(String key) throws Exception {
		logger.info("search method begins. Key:" + key);

		List<CustomSearchResponseDTO> dtos = null;

		try {
			String searchKey = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

			String url = buildSearchString(searchKey, 1, 10);

			String result = callGoogleCustomSearchApi(url);

			logger.info(result);

			dtos = convertJson2Object(result);
			
			if (!CollectionUtils.isEmpty(dtos)) {
				for (CustomSearchResponseDTO customSearchResponseDTO : dtos) {
					double ratio = calculateSimilarityRatio(key, customSearchResponseDTO.getTitle());
					
					customSearchResponseDTO.setRatio((long)(ratio * 100));
				}
			}

		} catch (Exception e) {
			logger.error(e, e);
		}

		logger.info("search method complated.");
		return dtos;
	}

	private List<CustomSearchResponseDTO> convertJson2Object(String resultJson) {
		logger.info("convertJson2Object method begins.");
		List<CustomSearchResponseDTO> dtos = new ArrayList<CustomSearchResponseDTO>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			JSONObject jsonObject = new JSONObject(resultJson);

			Object itemsJSONObj = jsonObject.get("items");

			if (StringUtils.isNotBlank(itemsJSONObj.toString())) {
				dtos = mapper.readValue(itemsJSONObj.toString(), new TypeReference<List<CustomSearchResponseDTO>>() {
				});

				logger.info(dtos.toString());
			}

		} catch (Exception e) {
			logger.error(e, e);
		}

		logger.info("convertJson2Object method begins.");
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
		
		toSearch += "&r=m&cx=007519843025510188404%3Alhku8uktzb0&client=google-coop&hl=tr&adsafe=low&type=0&pcsa=true&oe=UTF-8&ie=UTF-8&fexp=20606&format=p4&ad=p4&nocache=6931541534164139&num=0&output=uds_ads_only&source=gcsc&v=3&adext=as1%2Csr1&bsl=10&u_his=4&u_tz=180&dt=1541534164141&u_w=1920&u_h=1080&biw=1920&bih=150&psw=1904&psh=59&frm=0&uio=st16sd13sv13as1sl1sr1-&jsv=12107";

		logger.info("Seacrh URL: " + toSearch);

		return toSearch;
	}

	@Override
	public double calculateSimilarityRatio(String currentStr, String targetStr) {
		logger.info("calculateSimilarityRatio method begins. currentStr:" + currentStr + " targetStr:" + targetStr);

		double ratio = 0;

		int targetLength = targetStr.length();
		
		int distance = 0;
		
		currentStr = currentStr.toLowerCase(new Locale("UTF-8"));
		targetStr = targetStr.toLowerCase(new Locale("UTF-8"));

		int[] costs = new int[targetStr.length() + 1];
		for (int i = 0; i <= currentStr.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= targetStr.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (currentStr.charAt(i - 1) != targetStr.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[targetStr.length()] = lastValue;
		}
		
		distance = costs[targetStr.length()];

		ratio = (targetLength - distance) / (double) targetLength;

		logger.info("calculateSimilarityRatio method complated. Ratio:" + ratio);
		return ratio;
	}
}
