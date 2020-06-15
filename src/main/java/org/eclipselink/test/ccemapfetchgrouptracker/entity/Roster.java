package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"Rosters\"")
public class Roster implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column(name = "club_name")
    private String clubName;

    @Basic(optional = false)
    @Column(name = "ordinal_nbr")
    private Integer ordinalNbr;

    @OneToMany(mappedBy = "roster")
    private List<Score> scores;

    public Roster()
    {
    }

    public Roster(Integer id, String clubName, Integer ordinalNbr)
    {
        this.id = Objects.requireNonNull(id);
        this.clubName = clubName;
        this.ordinalNbr = ordinalNbr;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getClubName()
    {
        return clubName;
    }

    public void setClubName(String clubName)
    {
        this.clubName = clubName;
    }

    public Integer getOrdinalNbr()
    {
        return ordinalNbr;
    }

    public void setOrdinalNbr(Integer ordinalNbr)
    {
        this.ordinalNbr = ordinalNbr;
    }

    public List<Score> getScores()
    {
        return scores;
    }

    public void setScores(List<Score> scores)
    {
        this.scores = scores;
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
        Roster other = ( Roster ) obj;
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
        return "[" + id + ", " + clubName + ", " + ordinalNbr + "]";
    }
}
