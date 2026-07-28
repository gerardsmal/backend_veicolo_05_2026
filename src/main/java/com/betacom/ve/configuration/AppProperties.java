package com.betacom.ve.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

	private String clientDomain;
    private String resetPassword;
    private String sender;
    
    public String getUrlResetPassword() {
    	return clientDomain + resetPassword;
    }
}
