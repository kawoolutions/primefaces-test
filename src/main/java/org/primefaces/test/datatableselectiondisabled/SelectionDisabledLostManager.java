package org.primefaces.test.datatableselectiondisabled;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class SelectionDisabledLostManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String pfVersion;
    
    private List<TestDto> dtos;
    private TestDto selectedDto;
    
    public enum Mode { ADD, VIEW, EDIT, REMOVE };

    private Mode mode;
    
    @PostConstruct
    public void init()
    {
        pfVersion = PrimeFaces.class.getPackage().getImplementationVersion();
        
        dtos = Arrays.asList( new TestDto( "DIEB", 13, 13,  0, 1.000 ),
                              new TestDto( "TGRS", 13, 11,  2, 0.846 ),
                              new TestDto( "BABH", 13,  9,  4, 0.692 ),
                              new TestDto( "GINS", 13,  8,  5, 0.615 ),
                              new TestDto( "HOCH", 13,  6,  7, 0.462 ),
                              new TestDto( "BCDA", 13,  4,  9, 0.308 ),
                              new TestDto( "TVLA", 13,  3, 10, 0.214 ),
                              new TestDto( "BGOR", 13,  2, 11, 0.164 ) );
    }
    
    public String getPfVersion()
    {
        return pfVersion;
    }

    public void setPfVersion( String pfVersion )
    {
        this.pfVersion = pfVersion;
    }

    public List<TestDto> getDtos()
    {
        return dtos;
    }

    public void setDtos( List<TestDto> dtos )
    {
        this.dtos = dtos;
    }

    public TestDto getSelectedDto()
    {
        return selectedDto;
    }

    public void setSelectedDto( TestDto selectedDto )
    {
        this.selectedDto = selectedDto;
    }

    public Mode getMode()
    {
        return mode;
    }

    public void setMode( Mode mode )
    {
        this.mode = mode;
    }
    
//    public void add()
//    {
//        setMode( Mode.ADD );
//    }

    public void view()
    {
        setMode( Mode.VIEW );
    }
    
    public void edit()
    {
        setMode( Mode.EDIT );
    }
    
    public void cancel()
    {
        setSelectedDto( null );
        setMode( null );
    }
    
    public boolean isWriting()
    {
        return Arrays.asList( Mode.ADD, Mode.EDIT ).contains( getMode() );
    }
}
