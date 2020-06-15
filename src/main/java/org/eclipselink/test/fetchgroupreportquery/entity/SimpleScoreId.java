package org.eclipselink.test.fetchgroupreportquery.entity;

import java.io.Serializable;

public class SimpleScoreId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer simpleGame;

    private Boolean home;

    public SimpleScoreId()
    {
    }

    public SimpleScoreId(Integer gameId, Boolean home)
    {
        this.simpleGame = gameId;
        this.home = home;
    }

    public Integer getGameId()
    {
        return simpleGame;
    }

    public void setGameId(Integer gameId)
    {
        this.simpleGame = gameId;
    }

    public Boolean getHome()
    {
        return home;
    }

    public void setHome(Boolean home)
    {
        this.home = home;
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
        SimpleScoreId other = ( SimpleScoreId ) obj;
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
        return "[" + simpleGame + ", " + home + "]";
    }
}
