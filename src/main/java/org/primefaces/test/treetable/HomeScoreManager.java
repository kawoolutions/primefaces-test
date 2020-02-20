package org.primefaces.test.treetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@ViewScoped
public class HomeScoreManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String pfVersion;

    private TreeTable treeTable;
    private TreeNode rootNode;
    
    private List<PlayerStat> playerStats;

    @PostConstruct
    public void init()
    {
        pfVersion = PrimeFaces.class.getPackage().getImplementationVersion();
        
        playerStats = new ArrayList<>();
        
        playerStats.add( new PlayerStat( "Jordan", 23 ) );
        playerStats.add( new PlayerStat( "Nowitzki", 41 ) );
        playerStats.add( new PlayerStat( "Bird", 33 ) );
        playerStats.add( new PlayerStat( "Doncic", 77 ) );
    }
    
    public String getPfVersion()
    {
        return pfVersion;
    }

    public void setPfVersion( String pfVersion )
    {
        this.pfVersion = pfVersion;
    }

    public List<PlayerStat> getPlayerStats()
    {
        return playerStats;
    }

    public void setPlayerStats( List<PlayerStat> playerStats )
    {
        this.playerStats = playerStats;
    }

    public TreeTable getTreeTable()
    {
        return treeTable;
    }

    public void setTreeTable( TreeTable treeTable )
    {
        this.treeTable = treeTable;
    }

    public TreeNode getRootNode()
    {
        if ( rootNode == null )
        {
            // init navigation tree
            rootNode = new DefaultTreeNode( pfVersion, null );
            
            for ( PlayerStat playerStat : getPlayerStats() )
            {
//                log.info( "PS: " + playerStat.getName() + ", J# = " + playerStat.getJerseyNbr() );
                
                TreeNode playerStatNode = new DefaultTreeNode( PlayerStat.class.getSimpleName(), playerStat, rootNode );
                playerStatNode.setExpanded( true );
                
                // stats
                for ( Stat stat : playerStat.getStats() )
                {
                    @SuppressWarnings( "unused" )
                    TreeNode statNode = new DefaultTreeNode( Stat.class.getSimpleName(), stat, playerStatNode );
                }
            }
        }
        
        return rootNode;
    }

    public void setRootNode( TreeNode rootNode )
    {
        this.rootNode = rootNode;
    }
    
    /**
     * @param context
     * @param component
     * @param value
     */
    public void validatePlayerStats( FacesContext context, UIComponent component, Object value )
    {
        System.out.println( "validatePlayerStats()!" );
        
        int size = playerStats.size();
        
        if ( size == 0 )
        {
            // no player stat data: OK, just return
            return;
        }
        
        if ( size < 5 )
        {
            // not enough player stats
            throw new ValidatorException( new FacesMessage( FacesMessage.SEVERITY_ERROR, "There are " + size + " players participating, but there must be at least 5!", null ) );
        }
    }

    /**
     * @param context
     * @param component
     * @param value
     */
    public void validateStats( FacesContext context, UIComponent component, Object value )
    {
        System.out.println( "validateStats()!" );
        
        int size = playerStats.size();
        
        if ( size == 0 )
        {
            // no stat data: OK, just return
            return;
        }
        
        List<FacesMessage> msgs = new ArrayList<>();
        
        for ( PlayerStat playerStat : playerStats )
        {
            String playerName = playerStat.getPlayerName();
            
            for ( Stat stat : playerStat.getStats() )
            {
                if ( !isPlausible( stat ) )
                {
                    msgs.add( new FacesMessage( FacesMessage.SEVERITY_ERROR, "More FTM's than FTA's is not possible for " + playerName + " Q" + stat.getPeriod(), null ) );
                }
            }
        }
        
        if ( !msgs.isEmpty() )
        {
            // not enough player stats
            throw new ValidatorException( msgs );
        }
    }
    
    public boolean isPlausible( Stat stat )
    {
//        int tpm = stat.getTpm().intValue();
        int ftm = stat.getFtm().intValue();
        int fta = stat.getFta().intValue();
//        int pts = stat.getPts().intValue();
        
//        int tpPoints = tpm * 3;
//        int ftPoints = ftm;
//
//        int ftTpPoints = tpPoints + ftPoints;
//
//        int modFtTpPoints = ftTpPoints % 2;
//        int modPoints = pts % 2;

        // cannot make more free throws than attempted
        if ( ftm > fta )
        {
            return false;
        }
        
        return true;
    }
    
    public void save()
    {
        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_INFO, "Saved.", null ) );
    }
}
