package org.eclipselink.test.fetchgroupreportquery.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"Scores\"")
@IdClass(ScoreId.class)
public class Score implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_PLAYER_STATS = "Score.fetchPlayerStats";

    @Id
    @Column(name = "is_home", insertable = false, updatable = false)
    private Boolean home;

    @Basic
    @Column(name = "final_score")
    private Integer finalScore;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id")
    private Roster roster;

    public Score()
    {
    }

    public Score(Integer finalScore)
    {
        this(null, null, null, finalScore);
    }

    public Score(Integer gameId, Boolean home)
    {
        this(gameId, home, null);
    }

    public Score(Integer gameId, Boolean home, Integer rosterId)
    {
        this(gameId, home, rosterId, null);
    }

    public Score(Integer gameId, Boolean home, Integer rosterId, Integer finalScore)
    {
        this.home = Objects.requireNonNull(home);
        this.finalScore = finalScore;

        this.game = new Game(gameId);
        this.roster = new Roster(rosterId);
    }

    public Integer getGameId()
    {
        return game.getId();
    }

    public void setGameId(Integer gameId)
    {
        game.setId(gameId);
    }

    public Boolean getHome()
    {
        return home;
    }

    public void setHome(Boolean home)
    {
        this.home = home;
    }

    public Integer getRosterId()
    {
        return roster.getId();
    }

    public void setRosterId(Integer rosterId)
    {
        roster.setId(rosterId);
    }

    public Integer getFinalScore()
    {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore)
    {
        this.finalScore = finalScore;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public Roster getRoster()
    {
        return roster;
    }

    public void setRoster(Roster roster)
    {
        this.roster = roster;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (game == null) ? 0 : game.hashCode() );
        result = prime * result + ( (home == null) ? 0 : home.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Score other = ( Score ) obj;
        if ( game == null )
        {
            if ( other.game != null )
                return false;
        }
        else if ( !game.equals( other.game ) )
            return false;
        if ( home == null )
        {
            if ( other.home != null )
                return false;
        }
        else if ( !home.equals( other.home ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + home + ", " + finalScore + "]";
    }
}
