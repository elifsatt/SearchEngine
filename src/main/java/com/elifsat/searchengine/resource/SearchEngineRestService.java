package com.elifsat.searchengine.resource;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elifsat.searchengine.dto.CustomSearchResponseDTO;
import com.elifsat.searchengine.dto.ReturnDTO;
import com.elifsat.searchengine.service.ISearchEngineService;

@Component
@Path("/seacrhengine")
public class SearchEngineRestService {

	private static final Logger logger = LogManager.getLogger(SearchEngineRestService.class);

	@Autowired
	private ISearchEngineService seacrhEngineService;

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(@Context HttpServletRequest request, @QueryParam("key") String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("search REST method begins. Key:" + key);
		}

		ReturnDTO response = new ReturnDTO();
		try {
			
			key = URLDecoder.decode(key.replaceAll("\\+",  "%2B"), "UTF-8");
			//%20

			List<CustomSearchResponseDTO> list = seacrhEngineService.seacrh(key);

			if (CollectionUtils.isEmpty(list)) {
				logger.info("Result not found. Key:" + key);
				response.setStatus(false);
				response.setMessage("Result not found. Key:" + key);
			} else {
				response.setStatus(true);
				response.setResult(list);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("search REST method completed.");
		}
		return Response.ok(response).build();
	}
}
