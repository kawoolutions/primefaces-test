package org.primefaces.test.exceptionhandlerreloadpage;

import java.io.Serializable;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ExceptionHandlerView implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public void throwViewExpiredException()
    {
        throw new ViewExpiredException( "A ViewExpiredException!", FacesContext.getCurrentInstance().getViewRoot().getViewId() );
    }
    
    public void throwNullPointerException()
    {
        throw new NullPointerException( "A NullPointerException!" );
    }
    
    public void throwWrappedIllegalStateException()
    {
        throw new FacesException( new IllegalStateException( "A wrapped IllegalStateException!" ) );
    }
}