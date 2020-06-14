package org.eclipselink.test.ccemapfetchgrouptracker;

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

import org.eclipselink.test.ccemapfetchgrouptracker.entity.Game;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Player;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.PlayerStat;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Roster;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Score;

@Named
@ApplicationScoped
public class GameManager implements Serializable
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
        
        if ( this.emf == null )
        {
            System.out.println( "Programmatically creating entity manager factory..." );
            
            Map<String, String> properties = new HashMap<>();

            properties.put( "javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver" );
            properties.put( "javax.persistence.jdbc.url", "jdbc:hsqldb:mem:test" );
            properties.put( "javax.persistence.jdbc.user", "sa" );
            properties.put( "javax.persistence.jdbc.password", "" );

            properties.put( "eclipselink.target-database", "org.eclipse.persistence.platform.database.HSQLPlatform" );
            properties.put( "eclipselink.ddl-generation", "create-tables" );
            properties.put( "eclipselink.ddl-generation.output-mode", "database" );
            
//            properties.put( PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "src/main/resources/" + PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML_DEFAULT );
//            properties.put( PersistenceUnitProperties.WEAVING, "true" );
            
            this.emf = Persistence.createEntityManagerFactory( "TestPU", properties );
//            this.emf.
            
            // org.eclipse.persistence.jpa.PersistenceProvider
        }
        
        EntityManager em = emf.createEntityManager();

        System.out.println( "Entity manager: " + this.em );
        
        Player testPlayer = em.find( Player.class, 1 );
        
        if ( testPlayer == null )
        {
            // fill DB if player not found: lazy hack :-)
            
            this.et = em.getTransaction();
            this.et.begin();
            
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
                
                // add all player stats for home team
                Map<Integer, PlayerStat> homePlayerStats = new HashMap<>();
                int index = 0;
                
                for ( Player player : players )
                {
                    Integer jerseyNbr = index + 10;
                    
                    PlayerStat ps = new PlayerStat( jerseyNbr, Boolean.TRUE, Boolean.TRUE );
                    ps.setScore( homeScore );
                    ps.setPlayer( player );
                    
                    em.persist( ps );
                    
                    homePlayerStats.put( jerseyNbr, ps );
                    
                    index++;
                }
                
                homeScore.setPlayerStats( homePlayerStats );

                // add all player stats for away team
                Map<Integer, PlayerStat> awayPlayerStats = new HashMap<>();
                index = 0;
                
                for ( Player player : players )
                {
                    Integer jerseyNbr = index + 10;
                    
                    PlayerStat ps = new PlayerStat( jerseyNbr, Boolean.TRUE, Boolean.TRUE );
                    ps.setScore( awayScore );
                    ps.setPlayer( player );
                    
                    em.persist( ps );
                    
                    awayPlayerStats.put( jerseyNbr, ps );
                    
                    index++;
                }
                
                awayScore.setPlayerStats( awayPlayerStats );

                System.out.println( "Game after persist: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );
            }
            
            this.et.commit();
        }
        
        if ( this.em == null || !this.em.isOpen() )
        {
            this.em = em;
        }
    }
    
    private Game newGame( Roster homeRoster, Roster awayRoster )
    {
        Game game = new Game( 1, LocalDateTime.now() );
        wait( 500 );
        
        Score homeScore = new Score( Boolean.TRUE, null );
        homeScore.setRoster( homeRoster );
        homeScore.setGame( game );
        
        Score awayScore = new Score( Boolean.FALSE, null );
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
//        EntityManager em = emf.createEntityManager();
        
        List<Game> entities = null;
        
//        EntityGraph<?> graph = em.createEntityGraph( Game.FETCH_SCORES );
        EntityGraph<?> graph = em.getEntityGraph( Game.FETCH_SCORES_AND_PLAYER_STATS );
        TypedQuery<Game> query = em.createNamedQuery( Game.FIND_ALL, Game.class );
        
        query.setHint( "javax.persistence.fetchgraph", graph );
        
        entities = query.getResultList();
        
        System.out.println( "Number of games: " + entities.size() );

        for ( Game game : entities )
        {
            Score homeScore = game.getScores().get( Boolean.TRUE );
            Score awayScore = game.getScores().get( Boolean.FALSE );
            
            System.out.println( "Game after loading: " + NamingUtils.getGameLabelFor( game ) + ", home = " + homeScore + ", away = " + awayScore );
        }
        
        return entities;
    }
}