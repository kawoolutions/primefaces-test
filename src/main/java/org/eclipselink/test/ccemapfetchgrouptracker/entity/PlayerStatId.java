package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;

public class PlayerStatId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer player;

    private ScoreId score;

    public PlayerStatId()
    {
    }

    public PlayerStatId(Integer gameId, Boolean home, Integer playerId)
    {
        this.player = playerId;

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
        return player;
    }

    public void setPlayerId(Integer playerId)
    {
        this.player = playerId;
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
        PlayerStatId other = ( PlayerStatId ) obj;
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
        return "[" + player + ", " + score + "]";
    }
}
