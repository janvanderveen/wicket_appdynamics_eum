package com.greetz.sample.appdynamics.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.greetz.sample.appdynamics.components.AjaxSubmitForm;

/**
 * This is a simple page with some AJAX components to test the AJAX round-trip.
 * 
 * @author Jan van der Veen
 */
public class HomePage extends AbstractEndUserMonitoringPage 
{
	public HomePage()
	{
		add(new AjaxForm("form"));
	}
	
	private class AjaxForm extends AjaxSubmitForm
	{
		private static final long serialVersionUID = 1L;

		public AjaxForm(String id)
		{
			super(id);
			add(new FeedbackPanel("feedback"));
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			info("Hit me baby, one more time...");
			target.addComponent(this);
		}
	}
}
