package org.primefaces.test.multisort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@Named
@ViewScoped
public class MultiSortView extends MultiColumnSorter implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String pfVersion;
    
    private List<TestDto> dtos;

    private List<SortMeta> initialSortMetas;
    
    @PostConstruct
    public void init()
    {
        pfVersion = PrimeFaces.class.getPackage().getImplementationVersion();
        
        dtos = Arrays.asList( new TestDto( "DIEB", 13, 13, 0, 1.000 ),
                              new TestDto( "TGRS", 13, 11, 2, 0.846 ),
                              new TestDto( "BABH", 13,  9, 4, 0.692 ),
                              new TestDto( "GINS", 13,  9, 4, 0.692 ) );
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

    @Override
    public List<SortMeta> getInitialSortMetas()
    {
        if ( initialSortMetas == null )
        {
            initialSortMetas = new ArrayList<>();
            
            initialSortMetas.add( newSortMeta( "rs-standings-form:table:win-percentage", SortOrder.DESCENDING ) );
//            initialSortMetas.add( newSortMeta( "rs-standings-form:table:wins", SortOrder.DESCENDING ) );
//            initialSortMetas.add( newSortMeta( "rs-standings-form:table:losses", SortOrder.ASCENDING ) );
            initialSortMetas.add( newSortMeta( "rs-standings-form:table:games", SortOrder.DESCENDING ) );
            initialSortMetas.add( newSortMeta( "rs-standings-form:table:name", SortOrder.ASCENDING ) );
        }
        
        return initialSortMetas;
    }
}
