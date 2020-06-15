package org.eclipselink.test.fetchgroupreportquery;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.eclipselink.test.fetchgroupreportquery.entity.Game;
import org.eclipselink.test.fetchgroupreportquery.entity.Roster;
import org.eclipselink.test.fetchgroupreportquery.entity.Score;


@Named
@ApplicationScoped
public class GameScoreManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private List<Game> entities;

    @PersistenceUnit( unitName = "TestPU" )
    private EntityManagerFactory emf;
    
//    @PersistenceContext( name = "TestPU" )
    private EntityManager em;
    
    private EntityTransaction et;
    
    @PostConstruct
    public void init()
    {
        System.out.println( "Entity manager factory: " + emf );
        
        // usually not injectable, so create EMF manually
        if ( this.emf == null )
        {
            System.out.println( "Programmatically creating entity manager factory..." );
            
            // the EMF creation process REQUIRES a persistence.xml to be on the classpath AT ALL TIMES, see JPAInitializer.findPersistenceUnitInfoInArchives(String puName, Map m)
            // this is why setting the properties programmatically is kinda wasted energy: specify them in the persistence.xml
            this.emf = Persistence.createEntityManagerFactory( "TestPU" );
        }
        
        EntityManager em = emf.createEntityManager();
        this.em = em;

        System.out.println( "Entity manager: " + this.em );
        
        this.et = em.getTransaction();
        this.et.begin();
        
        List<Roster> rosters = new ArrayList<Roster>();
        rosters.add( new Roster( 1, "Dallas Mavericks", 3 ) );
        rosters.add( new Roster( 2, "Harlem Globetrotters", 2 ) );
        rosters.add( new Roster( 3, "Frankfurt Skyliners", 1 ) );
        rosters.add( new Roster( 4, "Chicago Bulls", 1 ) );
        rosters.add( new Roster( 5, "Boston Celtics", 4 ) );
        rosters.add( new Roster( 6, "New York Knicks", 3 ) );
        rosters.add( new Roster( 7, "Miami Heat", 2 ) );
        rosters.add( new Roster( 8, "Unicaya Malaga", 1 ) );
        
        rosters.forEach( r -> em.persist( r ) );
        
        Game game01 = newGame( rosters.get( 0 ), rosters.get( 1 ) );
        Game game02 = newGame( rosters.get( 2 ), rosters.get( 3 ) );
        Game game03 = newGame( rosters.get( 4 ), rosters.get( 5 ) );
        Game game04 = newGame( rosters.get( 6 ), rosters.get( 7 ) );

        List<Game> games = Arrays.asList( game01, game02, game03, game04 );
        
        for ( Game game : games )
        {
            Score homeScore = game.getScores().get( Boolean.TRUE );
            Score awayScore = game.getScores().get( Boolean.FALSE );
            
            System.out.println( "Game before persist: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );

            game.setScores( null );

            em.persist( game );
            
            em.persist( homeScore );
            em.persist( awayScore );
            
            Map<Boolean, Score> scores = new HashMap<>();
            scores.put( homeScore.getHome(), homeScore );
            scores.put( awayScore.getHome(), awayScore );
            
            game.setScores( scores );
            
            System.out.println( "Game after persist: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );
        }
        
        this.et.commit();
    }
    
    private Game newGame( Roster homeRoster, Roster awayRoster )
    {
        Game game = new Game( 1, LocalDateTime.now() );
        wait( 500 );
        
        Score homeScore = new Score( game.getId(), Boolean.TRUE, homeRoster.getId(), null );
        homeScore.setRoster( homeRoster );
        homeScore.setGame( game );
        
        Score awayScore = new Score( game.getId(), Boolean.FALSE, awayRoster.getId(), null );
        awayScore.setRoster( awayRoster );
        awayScore.setGame( game );
        
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
//        TypedQuery<Game> query = em.createNamedQuery( Game.FIND_ALL, Game.class );
        TypedQuery<Game> query = em.createNamedQuery( Game.FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID, Game.class );
        
        query.setHint( "javax.persistence.fetchgraph", graph );
        
        entities = query.getResultList();
        
        return entities;
    }
}