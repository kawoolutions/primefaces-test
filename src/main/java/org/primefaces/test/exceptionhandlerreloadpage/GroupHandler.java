package org.primefaces.test.exceptionhandlerreloadpage;

import java.io.Serializable;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GroupHandler implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Integer roundId;
    private String groupCode;
    
    public Integer getRoundId()
    {
        return roundId;
    }
    
    public void setRoundId( Integer roundId )
    {
        this.roundId = roundId;
    }
    
    public String getGroupCode()
    {
        return groupCode;
    }
    
    public void setGroupCode( String groupCode )
    {
        this.groupCode = groupCode;
    }
    
    public void loadEntity()
    {
        System.out.println( GroupHandler.class.getSimpleName() + ".loadEntity(): round ID = " + getRoundId() + ", group code = " + getGroupCode() );
    }
    
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
