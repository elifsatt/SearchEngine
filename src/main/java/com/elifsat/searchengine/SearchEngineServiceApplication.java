package com.elifsat.searchengine;

import java.util.Arrays;
import java.util.LinkedList;

import javax.ws.rs.Path;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@ComponentScan(basePackageClasses = SearchEngineServiceApplication.class)
@EnableAutoConfiguration
//@EnableConfigurationProperties(UserProperties.class)
@SpringBootApplication
@EnableCaching
@EnableScheduling
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
public class SearchEngineServiceApplication  extends SpringBootServletInitializer{

	
	private static final Logger logger = LogManager.getLogger(SearchEngineServiceApplication.class);

	@Autowired
	private ApplicationContext ctx;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		setRegisterErrorPageFilter(false);
		application.sources(SearchEngineServiceApplication.class);
		
		return application.sources(SearchEngineServiceApplication.class);
	}

	public static void main(String[] args) {
		logger.trace("SearchEngineServiceApplication main started.");
		SpringApplication.run(SearchEngineServiceApplication.class, args);
		logger.trace("SearchEngineServiceApplication main completed.");
	}

	@Bean
	public ServletRegistrationBean cxfServletRegistrationBean() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/services/*");
		registrationBean.setAsyncSupported(true);
		registrationBean.setLoadOnStartup(1);
		registrationBean.setName("CXFServlet");
		return registrationBean;
	}

	@Bean
	public Server jaxRsServer() {
		logger.trace("jaxRsServer started.");
		LinkedList<ResourceProvider> resourceProviders = new LinkedList<>();
		for (String beanName : ctx.getBeanDefinitionNames()) {
			if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
				SpringResourceFactory factory = new SpringResourceFactory(beanName);
				factory.setApplicationContext(ctx);
				resourceProviders.add(factory);
			}
		}
		if (!resourceProviders.isEmpty()) {
			JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
			factory.setBus(ctx.getBean(SpringBus.class));
			factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
			factory.setResourceProviders(resourceProviders);
			return factory.create();
		} else {
			return null;
		}
	}

}
