package org.primefaces.test.multisort;

import java.io.Serializable;

public class TestDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer games;
    private Integer wins;
    private Integer losses;
    private double winp;
    
    public TestDto( String name, Integer games, Integer wins, Integer losses, double winp )
    {
        this.name = name;
        this.games = games;
        this.wins = wins;
        this.losses = losses;
        this.winp = winp;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getGames()
    {
        return games;
    }

    public void setGames( Integer games )
    {
        this.games = games;
    }

    public Integer getWins()
    {
        return wins;
    }

    public void setWins( Integer wins )
    {
        this.wins = wins;
    }

    public Integer getLosses()
    {
        return losses;
    }

    public void setLosses( Integer losses )
    {
        this.losses = losses;
    }

    public double getWinp()
    {
        return winp;
    }

    public void setWinp( double winp )
    {
        this.winp = winp;
    }
}
