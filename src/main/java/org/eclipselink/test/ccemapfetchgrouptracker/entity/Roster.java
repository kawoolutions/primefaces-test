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
    private Integer clubName;

    @Basic(optional = false)
    @Column(name = "team_type_code")
    private String teamTypeCode;

    @Basic(optional = false)
    @Column(name = "team_ordinal_nbr")
    private Integer teamOrdinalNbr;

    @Basic(optional = false)
    @Column(name = "season_start_year")
    private Integer seasonStartYear;

    @Basic
    @Column(name = "jersey_color_name")
    private String jerseyColorName;

    @OneToMany(mappedBy = "roster")
    private List<Score> scores;

    public Roster()
    {
    }

    public Roster(Integer id)
    {
        this(id, (Integer) null, null, null, null);
    }

    public Roster(Integer id, Integer clubName, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear)
    {
        this(id, clubName, teamTypeCode, teamOrdinalNbr, seasonStartYear, null);
    }

    public Roster(Integer clubName, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear, String jerseyColorName)
    {
        this(null, clubName, teamTypeCode, teamOrdinalNbr, seasonStartYear, jerseyColorName);
    }

    public Roster(Integer id, Integer clubName, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear, String jerseyColorName)
    {
        this.id = Objects.requireNonNull(id);
        this.clubName = clubName;
        this.teamTypeCode = teamTypeCode;
        this.teamOrdinalNbr = teamOrdinalNbr;
        this.seasonStartYear = seasonStartYear;
        this.jerseyColorName = jerseyColorName;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getClubName()
    {
        return clubName;
    }

    public void setClubName(Integer clubName)
    {
        this.clubName = clubName;
    }

    public String getTeamTypeCode()
    {
        return teamTypeCode;
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        this.teamTypeCode = teamTypeCode;
    }

    public Integer getTeamOrdinalNbr()
    {
        return teamOrdinalNbr;
    }

    public void setTeamOrdinalNbr(Integer teamOrdinalNbr)
    {
        this.teamOrdinalNbr = teamOrdinalNbr;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        this.seasonStartYear = seasonStartYear;
    }

    public String getJerseyColorName()
    {
        return jerseyColorName;
    }

    public void setJerseyColorName(String jerseyColorName)
    {
        this.jerseyColorName = jerseyColorName;
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
        return "[" + id + ", " + clubName + ", " + teamTypeCode + ", " + teamOrdinalNbr + ", " + seasonStartYear + ", " + jerseyColorName + "]";
    }
}
