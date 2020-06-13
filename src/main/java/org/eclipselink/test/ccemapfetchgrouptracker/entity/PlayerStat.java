package org.eclipselink.test.ccemapfetchgrouptracker.entity;

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
@Table(name = "\"PlayerStats\"")
@IdClass(PlayerStatId.class)
public class PlayerStat implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Basic(optional = false)
    @Column(name = "jersey_nbr")
    private Integer jerseyNbr;

    @Basic(optional = false)
    @Column(name = "has_played")
    private Boolean hasPlayed = Boolean.TRUE;

    @Basic
    @Column(name = "is_starter")
    private Boolean starter;

    @Basic(optional = false)
    @Column
    private Integer pf;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @JoinColumn(name = "is_home", referencedColumnName = "is_home")
    private Score score;

    public PlayerStat()
    {
    }

    public PlayerStat(PlayerStat p)
    {
        this(p.getGameId(), p.getHome(), p.getPlayerId(), p.getJerseyNbr(), p.getStarter(), p.getPf());

        this.hasPlayed = p.getHasPlayed();
    }

    public PlayerStat(Integer gameId, Boolean home, Integer playerId)
    {
        this(gameId, home, playerId, null, null);
    }

    public PlayerStat(Integer gameId, Boolean home, Integer playerId, Integer jerseyNbr, Integer pf)
    {
        this(gameId, home, playerId, jerseyNbr, null, pf);
    }

    public PlayerStat(Integer gameId, Boolean home, Integer playerId, Integer jerseyNbr, Boolean starter, Integer pf)
    {
        this.playerId = Objects.requireNonNull(playerId);
        this.jerseyNbr = jerseyNbr;
        this.starter = starter;
        this.pf = pf;

        this.score = new Score(gameId, home);
    }

    public Integer getGameId()
    {
        return score.getGameId();
    }

    public void setGameId(Integer gameId)
    {
        score.setGameId(gameId);
    }

    public Boolean getHome()
    {
        return score.getHome();
    }

    public void setHome(Boolean home)
    {
        score.setHome(home);
    }

    public Integer getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(Integer playerId)
    {
        this.playerId = playerId;
    }

    public Integer getJerseyNbr()
    {
        return jerseyNbr;
    }

    public void setJerseyNbr(Integer jerseyNbr)
    {
        this.jerseyNbr = jerseyNbr;
    }

    public Boolean getHasPlayed()
    {
        return hasPlayed;
    }

    public void setHasPlayed(Boolean hasPlayed)
    {
        this.hasPlayed = hasPlayed;
    }

    public Boolean getStarter()
    {
        return starter;
    }

    public void setStarter(Boolean starter)
    {
        this.starter = starter;
    }

    public Integer getPf()
    {
        return pf;
    }

    public void setPf(Integer pf)
    {
        this.pf = pf;
    }

    public Score getScore()
    {
        return score;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (playerId == null) ? 0 : playerId.hashCode() );
        result = prime * result + ( (score == null) ? 0 : score.hashCode() );
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
        PlayerStat other = ( PlayerStat ) obj;
        if ( playerId == null )
        {
            if ( other.playerId != null )
                return false;
        }
        else if ( !playerId.equals( other.playerId ) )
            return false;
        if ( score == null )
        {
            if ( other.score != null )
                return false;
        }
        else if ( !score.equals( other.score ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + playerId + ", " + jerseyNbr + ", " + hasPlayed + ", " + starter + ", " + pf + "]";
    }
}
