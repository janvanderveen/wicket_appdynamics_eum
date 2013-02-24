package com.greetz.sample.appdynamics.application;

import com.greetz.sample.appdynamics.pages.HomePage;

public class WebApplication extends org.apache.wicket.protocol.http.WebApplication 
{
	@Override
	public Class<?> getHomePage() 
	{
		return HomePage.class;
	}
}
