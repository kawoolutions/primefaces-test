package org.eclipselink.test.fetchgroupreportquery.entity;

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
@Table(name = "\"_Simple_Rosters\"")
public class SimpleRoster implements Serializable
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

    @Basic
    @Column(name = "jersey_color")
    private String jerseyColor;

    @OneToMany(mappedBy = "simpleRoster")
    private List<SimpleScore> simpleScores;

    public SimpleRoster()
    {
    }

    public SimpleRoster(Integer id)
    {
        this(id, null, null);
    }

    public SimpleRoster(Integer id, String clubName, Integer ordinalNbr)
    {
        this(id, clubName, ordinalNbr, null);
    }

    public SimpleRoster(String clubName, Integer ordinalNbr, String jerseyColor)
    {
        this(null, clubName, ordinalNbr, jerseyColor);
    }

    public SimpleRoster(Integer id, String clubName, Integer ordinalNbr, String jerseyColor)
    {
        this.id = Objects.requireNonNull(id);
        this.clubName = clubName;
        this.ordinalNbr = ordinalNbr;
        this.jerseyColor = jerseyColor;
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

    public String getJerseyColor()
    {
        return jerseyColor;
    }

    public void setJerseyColor(String jerseyColor)
    {
        this.jerseyColor = jerseyColor;
    }

    public List<SimpleScore> getSimpleScores()
    {
        return simpleScores;
    }

    public void setSimpleScores(List<SimpleScore> simpleScores)
    {
        this.simpleScores = simpleScores;
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
        SimpleRoster other = ( SimpleRoster ) obj;
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
        return "[" + id + ", " + clubName + ", " + ordinalNbr + ", " + jerseyColor + "]";
    }
}
