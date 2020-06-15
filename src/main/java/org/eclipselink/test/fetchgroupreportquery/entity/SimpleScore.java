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
@Table(name = "\"_Simple_Scores\"")
@IdClass(SimpleScoreId.class)
public class SimpleScore implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "is_home")
    private Boolean home;

    @Basic
    @Column(name = "final_score")
    private Integer finalScore;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private SimpleGame simpleGame;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id")
    private SimpleRoster simpleRoster;

    public SimpleScore()
    {
    }

    public SimpleScore(Integer gameId, Boolean home, Integer rosterId, Integer finalScore)
    {
        this.home = Objects.requireNonNull(home);
        this.finalScore = finalScore;

        this.simpleGame = new SimpleGame();
        this.simpleGame.setId(gameId);

        this.simpleRoster = new SimpleRoster();
        this.simpleRoster.setId(rosterId);
    }

    public Integer getGameId()
    {
        return simpleGame.getId();
    }

    public void setGameId(Integer gameId)
    {
        simpleGame.setId(gameId);
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
        return simpleRoster.getId();
    }

    public void setRosterId(Integer rosterId)
    {
        simpleRoster.setId(rosterId);
    }

    public Integer getFinalScore()
    {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore)
    {
        this.finalScore = finalScore;
    }

    public SimpleGame getSimpleGame()
    {
        return simpleGame;
    }

    public void setSimpleGame(SimpleGame simpleGame)
    {
        this.simpleGame = simpleGame;
    }

    public SimpleRoster getSimpleRoster()
    {
        return simpleRoster;
    }

    public void setSimpleRoster(SimpleRoster simpleRoster)
    {
        this.simpleRoster = simpleRoster;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (home == null) ? 0 : home.hashCode() );
        result = prime * result + ( (simpleGame == null) ? 0 : simpleGame.hashCode() );
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
        SimpleScore other = ( SimpleScore ) obj;
        if ( home == null )
        {
            if ( other.home != null )
                return false;
        }
        else if ( !home.equals( other.home ) )
            return false;
        if ( simpleGame == null )
        {
            if ( other.simpleGame != null )
                return false;
        }
        else if ( !simpleGame.equals( other.simpleGame ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + home + ", " + finalScore + "]";
    }
}
