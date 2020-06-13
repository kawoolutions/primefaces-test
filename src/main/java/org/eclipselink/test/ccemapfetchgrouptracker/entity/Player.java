package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "\"Players\"")
public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column
    private Integer id;

    @Basic
    @Column(name = "registration_nbr")
    private String registrationNbr;

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

    public Player(Integer id, String registrationNbr)
    {
        this.id = id;
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
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
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + id + ", " + registrationNbr + "]";
    }
}
