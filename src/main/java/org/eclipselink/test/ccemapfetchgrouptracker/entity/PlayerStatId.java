package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.util.Objects;

public class PlayerStatId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer playerId;

    private ScoreId score;

    public PlayerStatId()
    {
    }

    public PlayerStatId(PlayerStatId p)
    {
        this(p.getGameId(), p.getHome(), p.getPlayerId());
    }

    public PlayerStatId(Integer gameId, Boolean home, Integer playerId)
    {
        this.playerId = Objects.requireNonNull(playerId);

        this.score = new ScoreId(gameId, home);
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

    public ScoreId getScoreId()
    {
        return score;
    }

    public void setScoreId(ScoreId scoreId)
    {
        this.score = scoreId;
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
        PlayerStatId other = ( PlayerStatId ) obj;
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
        return "[" + playerId + ", " + score + "]";
    }
}