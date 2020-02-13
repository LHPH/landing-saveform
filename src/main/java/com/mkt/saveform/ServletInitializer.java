package com.mkt.saveform;

import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.mkt.core.config.LoggerConfig;
import com.mkt.core.config.MessageConfig;
import com.mkt.core.config.ValidatorsConfig;
import com.mkt.core.model.Message;

@SpringBootApplication
@EntityScan(basePackages= {"com.mkt.core.entity"})
@Import(value= {MessageConfig.class,ValidatorsConfig.class})
public class ServletInitializer extends SpringBootServletInitializer {
	
	static {
		ConfigurationFactory custom = new LoggerConfig(LoggerConfig.LEVEL_DEV);
		ConfigurationFactory.setConfigurationFactory(custom);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServletInitializer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServletInitializer.class, args);
	}

}
