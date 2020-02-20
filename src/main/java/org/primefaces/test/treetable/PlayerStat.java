package org.primefaces.test.treetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerStat implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String playerName;
    private Integer jerseyNbr;

    private List<Stat> stats;

    public PlayerStat(String playerName, Integer jerseyNbr)
    {
        this.playerName = playerName;
        this.jerseyNbr = jerseyNbr;
        
        stats = newStats();
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public Integer getJerseyNbr()
    {
        return jerseyNbr;
    }

    public void setJerseyNbr(Integer jerseyNbr)
    {
        this.jerseyNbr = jerseyNbr;
    }

    public List<Stat> getStats()
    {
        return stats;
    }

    public void setStats(List<Stat> stats)
    {
        this.stats = stats;
    }
    
    private List<Stat> newStats()
    {
        List<Stat> stats = new ArrayList<>();
        
        // generate 1, 2, 3, 4 -> 4 quarters
        for ( Integer period : Arrays.asList( 1, 2, 3, 4 ) )
        {
            stats.add( new Stat( period, 0, 0, 0, 0 ) );
        }
        
        return stats;
    }
}
