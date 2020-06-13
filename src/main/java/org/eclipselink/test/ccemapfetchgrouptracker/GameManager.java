package org.eclipselink.test.ccemapfetchgrouptracker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.eclipselink.test.ccemapfetchgrouptracker.entity.Game;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Player;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Roster;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Score;

@Named
@ViewScoped
public class GameManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private List<Game> entities;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void init()
    {
        List<Player> players = new ArrayList<Player>();
        players.add( new Player( 1, "Bird" ) );
        players.add( new Player( 2, "Jordan" ) );
        players.add( new Player( 3, "Duncan" ) );
        players.add( new Player( 4, "Nowitzki" ) );
        players.add( new Player( 5, "Doncic" ) );
        
        players.forEach( p -> em.persist( p ) );
        
        List<Roster> rosters = new ArrayList<Roster>();
        rosters.add( new Roster( 1, "Dallas Mavericks", 3 ) );
        rosters.add( new Roster( 2, "Harlem Globetrotters", 2 ) );
        rosters.add( new Roster( 3, "Frankfurt Skyliners", 1 ) );
        rosters.add( new Roster( 4, "The Daltons", 1 ) );
        
        rosters.forEach( r -> em.persist( r ) );
        
        Game game01 = newGame( rosters.get( 0 ), rosters.get( 1 ) );
        Game game02 = newGame( rosters.get( 2 ), rosters.get( 3 ) );
        Game game03 = newGame( rosters.get( 0 ), rosters.get( 3 ) );
        Game game04 = newGame( rosters.get( 2 ), rosters.get( 1 ) );

        List<Game> games = Arrays.asList( game01, game02, game03, game04 );
        
        for ( Game game : games )
        {
            em.persist( game );
            
            em.persist( game.getScores().get( Boolean.TRUE ) );
            em.persist( game.getScores().get( Boolean.FALSE ) );
        }
    }
    
    private Game newGame( Roster homeRoster, Roster awayRoster )
    {
        Game game = new Game( 1, LocalDateTime.now() );
        wait( 500 );
        
        Score homeScore = new Score( game.getId(), Boolean.TRUE );
        homeScore.setRoster( homeRoster );
        homeScore.setRosterId( homeRoster.getId() );
        
        Score awayScore = new Score( game.getId(), Boolean.FALSE );
        awayScore.setRoster( awayRoster );
        awayScore.setRosterId( awayRoster.getId() );
        
        Map<Boolean, Score> scores = new HashMap<>();
        scores.put( homeScore.getHome(), homeScore );
        scores.put( awayScore.getHome(), awayScore );
        
        game.setScores( scores );

        return game;
    }
    
    private void wait( int msec )
    {
        try
        {
            Thread.sleep( msec );
        }
        catch ( InterruptedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Game> getEntities()
    {
        if ( entities == null )
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
        
        return entities;
    }

    public void setEntities( List<Game> entities )
    {
        this.entities = entities;
    }
    
    protected List<Game> loadEntities() throws Exception
    {
        List<Game> entities = null;
        
        EntityGraph<?> graph = em.createEntityGraph( Game.FETCH_SCORES );
//        EntityGraph<?> graph = em.getEntityGraph( Game.FETCH_SCORES_AND_PLAYER_STATS );
        TypedQuery<Game> query = em.createNamedQuery( Game.FIND_ALL, Game.class );
        
        query.setHint( "javax.persistence.fetchgraph", graph );
        
        entities = query.getResultList();
        
        return entities;
    }
}