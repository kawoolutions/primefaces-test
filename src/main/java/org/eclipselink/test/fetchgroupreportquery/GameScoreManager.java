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
import javax.persistence.TypedQuery;

import org.eclipselink.test.fetchgroupreportquery.entity.SimpleGame;
import org.eclipselink.test.fetchgroupreportquery.entity.SimpleRoster;
import org.eclipselink.test.fetchgroupreportquery.entity.SimpleScore;


@Named
@ApplicationScoped
public class GameScoreManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private List<SimpleGame> entities;

//    @PersistenceUnit( unitName = "TestPU" )
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
        
        List<SimpleRoster> rosters = new ArrayList<>();
        rosters.add( new SimpleRoster( 1, "Dallas Mavericks", 3 ) );
        rosters.add( new SimpleRoster( 2, "Harlem Globetrotters", 2 ) );
        rosters.add( new SimpleRoster( 3, "Frankfurt Skyliners", 1 ) );
        rosters.add( new SimpleRoster( 4, "Chicago Bulls", 1 ) );
        rosters.add( new SimpleRoster( 5, "Boston Celtics", 4 ) );
        rosters.add( new SimpleRoster( 6, "New York Knicks", 3 ) );
        rosters.add( new SimpleRoster( 7, "Miami Heat", 2 ) );
        rosters.add( new SimpleRoster( 8, "Unicaya Malaga", 1 ) );
        
        rosters.forEach( r -> em.persist( r ) );
        
        SimpleGame game01 = newGame( rosters.get( 0 ), rosters.get( 1 ) );
        SimpleGame game02 = newGame( rosters.get( 2 ), rosters.get( 3 ) );
        SimpleGame game03 = newGame( rosters.get( 4 ), rosters.get( 5 ) );
        SimpleGame game04 = newGame( rosters.get( 6 ), rosters.get( 7 ) );

        List<SimpleGame> games = Arrays.asList( game01, game02, game03, game04 );
        
        for ( SimpleGame game : games )
        {
            SimpleScore homeScore = game.getSimpleScores().get( Boolean.TRUE );
            SimpleScore awayScore = game.getSimpleScores().get( Boolean.FALSE );
            
            System.out.println( "Game before persist: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );

            game.setSimpleScores( null );

            em.persist( game );
            
            em.persist( homeScore );
            em.persist( awayScore );
            
            Map<Boolean, SimpleScore> scores = new HashMap<>();
            scores.put( homeScore.getHome(), homeScore );
            scores.put( awayScore.getHome(), awayScore );
            
            game.setSimpleScores( scores );
            
            System.out.println( "Game after persist: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );
        }
        
        this.et.commit();
    }
    
    private SimpleGame newGame( SimpleRoster homeRoster, SimpleRoster awayRoster )
    {
        SimpleGame game = new SimpleGame( 1, LocalDateTime.now() );
        
        try
        {
            Thread.sleep( 500 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        
        SimpleScore homeScore = new SimpleScore( game.getId(), Boolean.TRUE, homeRoster.getId(), null );
        homeScore.setSimpleRoster( homeRoster );
        homeScore.setSimpleGame( game );
        
        SimpleScore awayScore = new SimpleScore( game.getId(), Boolean.FALSE, awayRoster.getId(), null );
        awayScore.setSimpleRoster( awayRoster );
        awayScore.setSimpleGame( game );
        
        Map<Boolean, SimpleScore> scores = new HashMap<>();
        scores.put( homeScore.getHome(), homeScore );
        scores.put( awayScore.getHome(), awayScore );
        
        game.setSimpleScores( scores );

        return game;
    }
    
    public List<SimpleGame> getEntities()
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

    public void setEntities( List<SimpleGame> entities )
    {
        this.entities = entities;
    }
    
    protected List<SimpleGame> loadEntities() throws Exception
    {
        List<SimpleGame> entities = null;
        
//        TypedQuery<SimpleGame> query = em.createNamedQuery( SimpleGame.FIND_ALL, Game.class );
        TypedQuery<SimpleGame> query = em.createNamedQuery( SimpleGame.FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID, SimpleGame.class );
        
        EntityGraph<?> graph = em.createEntityGraph( SimpleGame.FETCH_SCORES );
        query.setHint( "javax.persistence.fetchgraph", graph );
        
        entities = query.getResultList();
        
        return entities;
    }
}