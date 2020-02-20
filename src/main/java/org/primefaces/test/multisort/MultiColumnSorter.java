package org.primefaces.test.multisort;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIColumn;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public abstract class MultiColumnSorter
{
    /**
     * https://stackoverflow.com/questions/15199805/initial-sortorder-for-primefaces-datatable-with-multisort
     */
    public SortMeta newSortMeta( String columnId, SortOrder sortOrder )
    {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent column = viewRoot.findComponent( columnId );

        if ( column == null )
        {
            throw new IllegalArgumentException( "Column " + columnId + " not found in view root!" );
        }
        
        UIColumn sortColumn = ( UIColumn ) column;

        // PF 8.0.RC3
        SortMeta sortMeta = new SortMeta( sortColumn.getColumnKey(), columnId, sortOrder, null );
        
        // PF 7.0, master-SNAPSHOT, ...
//        SortMeta sortMeta = new SortMeta( sortColumn, columnId, sortOrder, null );

//        System.out.println( "SortMeta: " + columnId + ", " + sortOrder );
        
        return sortMeta;
    }
    
    public abstract List<SortMeta> getInitialSortMetas();
}
