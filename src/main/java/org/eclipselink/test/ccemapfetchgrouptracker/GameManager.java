package org.eclipselink.test.ccemapfetchgrouptracker;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.eclipselink.test.ccemapfetchgrouptracker.entity.Game;

@Named
@ViewScoped
public class GameManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private List<Game> entities;
    
    @PersistenceContext
    private EntityManager em;
    
//    @Inject
//    private GameService gameService;
    
    @PostConstruct
    public void init()
    {
        try
        {
            this.entities = loadEntities();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    protected List<Game> loadEntities() throws Exception
    {
        List<Game> entities = null;
        
//        EntityGraph graph = em.createEntityGraph( Game.FETCH_SCORES );
        EntityGraph<?> graph = em.getEntityGraph( Game.FETCH_SCORES_AND_PLAYER_STATS );
        TypedQuery<Game> query = em.createNamedQuery( Game.FIND_ALL, Game.class );
        
        query.setHint( "javax.persistence.fetchgraph", graph );
        
        entities = query.getResultList();
        
//        entities = gameService.findByNamedQuery( Game.FIND_ALL,  );
        
        return entities;
    }

    public List<Game> getEntities()
    {
        return entities;
    }

    public void setEntities( List<Game> entities )
    {
        this.entities = entities;
    }
}