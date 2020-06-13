package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"Players\"")
public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Integer id;

    @Basic
    @Column(name = "registration_nbr")
    private String registrationNbr;

    @OneToMany(mappedBy = "player")
    private List<PlayerStat> playerStats;

    public Player()
    {
    }

    public Player(Player p)
    {
        this(p.getId(), p.getRegistrationNbr());
    }

    public Player(Integer id)
    {
        this(id, null);
    }

    public Player(String registrationNbr)
    {
        this(null, registrationNbr);
    }

    public Player(Integer id, String registrationNbr)
    {
        this.id = Objects.requireNonNull(id);
        this.registrationNbr = registrationNbr;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getRegistrationNbr()
    {
        return registrationNbr;
    }

    public void setRegistrationNbr(String registrationNbr)
    {
        this.registrationNbr = registrationNbr;
    }

    public List<PlayerStat> getPlayerStats()
    {
        return playerStats;
    }

    public void setPlayerStats(List<PlayerStat> playerStats)
    {
        this.playerStats = playerStats;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode() );
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
        Player other = ( Player ) obj;
        if ( id == null )
        {
            if ( other.id != null )
                return false;
        }
        else if ( !id.equals( other.id ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + id + ", " + registrationNbr + "]";
    }
}
