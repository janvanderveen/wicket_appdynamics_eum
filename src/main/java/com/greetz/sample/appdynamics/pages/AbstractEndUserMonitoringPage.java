package com.greetz.sample.appdynamics.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

/**
 * This abstract page can be extended by any other page to provide AppDynamics EUM support.
 * It will pick up the values for the header and footer through request attributes, which
 * is called "manual mode" in AppDynamics.
 * 
 * @author Jan van der Veen
 */
public abstract class AbstractEndUserMonitoringPage extends WebPage 
{
	private static final String APP_DYANMICS_HEADER_PARAM_NAME = "AppDynamics_JS_HEADER";
    private static final String APP_DYANMICS_FOOTER_PARAM_NAME = "AppDynamics_JS_FOOTER";
    
    public AbstractEndUserMonitoringPage()
    {
        this(new PageParameters());
    }

    public AbstractEndUserMonitoringPage(PageParameters parameters)
	{
    	super(parameters);
        // Initialize AppDynamics EUM
        initializeEndUserMonitoring();
	}
    
    private void initializeEndUserMonitoring()
    {
    	// Check if the session attributes are present (manual mode)
    	if (isAppDynamicsSessionAttributesPresent())
    	{
    		// Add the header value through a header contributor
		    add(new AppDynamicsHeaderContributor());
		    
		    // Add the footer value inside the page's markup
		    String footerValue = String.valueOf(getHttpRequest().getAttribute(APP_DYANMICS_FOOTER_PARAM_NAME));
	        Label appDynamicsFooter = new Label("appDynamicsFooter", footerValue);
	        appDynamicsFooter.setEscapeModelStrings(false);
	        add(appDynamicsFooter);
    	}
    	else
    	{
    		add(new WebMarkupContainer("appDynamicsFooter").setVisible(false));
    	}
    }
    
    private boolean isAppDynamicsSessionAttributesPresent()
    {
        // Check if the EUM request attributes are set and are not empty
        if (StringUtils.isNotBlank(getRequestAttribute(APP_DYANMICS_HEADER_PARAM_NAME)) && StringUtils.isNotBlank(getRequestAttribute(APP_DYANMICS_FOOTER_PARAM_NAME)))
        {
            return true;
        }
        return false;
    }
    
    private String getRequestAttribute(String name)
    {
    	HttpServletRequest request = getHttpRequest();
    	Object attributeValue = request.getAttribute(name);
    	if (attributeValue != null)
    	{
    		return String.valueOf(attributeValue);
    	}
    	return null;
    }
    
    private HttpServletRequest getHttpRequest()
    {
        return ((WebRequest) WebRequestCycle.get().getRequest()).getHttpServletRequest();
    }
    
    private class AppDynamicsHeaderContributor extends AbstractBehavior implements IHeaderContributor
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void renderHead(IHeaderResponse response)
        {
        	HttpServletRequest request = getHttpRequest();
            // Render out the header value as-is
        	String headerValue = String.valueOf(request.getAttribute(APP_DYANMICS_HEADER_PARAM_NAME));
            response.renderString(headerValue);
        }
    }
}
