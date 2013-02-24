package com.greetz.sample.appdynamics.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * An abstract Form that can be used as an a form with ajax submits.
 */
public abstract class AjaxSubmitForm extends Form
{
    private static final long serialVersionUID = 1L;

	public AjaxSubmitForm(final String id)
    {
        super(id);
        addAjaxSubmitBehavior();
    }

    public AjaxSubmitForm(final String id, final IModel model)
    {
        super(id, model);
        addAjaxSubmitBehavior();
    }

    /**
     * Creates an Ajax call decorator which wall append "return false;" to the
     * submit script so the form will not submit itself using an HTTP Post
     * (since the data is already being sent in an Ajax post)
     */
    protected AjaxCallDecorator getAjaxCallDecorator()
    {
        return new AjaxCallDecorator()
        {
            private static final long serialVersionUID = 1L;

            @Override
			public CharSequence decorateScript(CharSequence script)
            {
                return script + "return false";
            }
        };
    }

    /**
     * Defines the event handler for Ajax form submits.
     */
    protected abstract void onSubmit(final AjaxRequestTarget target);

    /**
     * Appends a script which handles the various actions usually performed on page load
     * like styling buttons, initializing tooltips & validation messages etc. Default
     * implementation will add this form to the target so validation messages are
     * displayed.
     */
    protected void onError(final AjaxRequestTarget target)
    {
        target.addComponent(this);
    }

    /**
     * Adds ajaxFormSumbitBeavior to this form. The behavior will delegate onError,
     * onSumbit and getAjaxCallDecorater to this form;
     */
    protected void addAjaxSubmitBehavior()
    {
        add(new AjaxFormSubmitBehavior(this, "onsubmit")
        {
        	private static final long serialVersionUID = 1L;

            @Override
			protected IAjaxCallDecorator getAjaxCallDecorator()
            {
                return AjaxSubmitForm.this.getAjaxCallDecorator();
            }

            @Override
			protected void onError(final AjaxRequestTarget target)
            {
                AjaxSubmitForm.this.onError(target);
            }

            @Override
			protected void onSubmit(final AjaxRequestTarget target)
            {
                AjaxSubmitForm.this.onSubmit(target);
            }
        });
    }
}
