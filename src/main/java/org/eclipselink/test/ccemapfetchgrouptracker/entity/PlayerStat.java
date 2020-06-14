package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;

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

    @Basic(optional = false)
    @Column(name = "jersey_nbr")
    private Integer jerseyNbr;

    @Basic(optional = false)
    @Column(name = "has_played")
    private Boolean hasPlayed = Boolean.TRUE;

    @Basic
    @Column(name = "is_starter")
    private Boolean starter;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @JoinColumn(name = "is_home", referencedColumnName = "is_home")
    private Score score;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    public PlayerStat()
    {
    }

    public PlayerStat(Integer jerseyNbr, Boolean hasPlayed, Boolean starter)
    {
        this.jerseyNbr = jerseyNbr;
        this.hasPlayed = hasPlayed;
        this.starter = starter;
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
        return player.getId();
    }

    public void setPlayerId(Integer playerId)
    {
        player.setId(playerId);
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

    public Score getScore()
    {
        return score;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (player == null) ? 0 : player.hashCode() );
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
        if ( player == null )
        {
            if ( other.player != null )
                return false;
        }
        else if ( !player.equals( other.player ) )
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
        return "[" + jerseyNbr + ", " + hasPlayed + ", " + starter + "]";
    }
}
