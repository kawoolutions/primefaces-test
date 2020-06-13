package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "\"Games\"")
@NamedQuery(name = Game.FIND_ALL, query = "SELECT ga FROM Game ga")
@NamedEntityGraph(name = Game.FETCH_SCORES, attributeNodes = {@NamedAttributeNode("scores")})
@NamedEntityGraph(name = Game.FETCH_SCORES_AND_PLAYER_STATS,
                  attributeNodes = {@NamedAttributeNode(value = "scores", subgraph = Score.FETCH_PLAYER_STATS)},
                  subgraphs = {@NamedSubgraph(name = Score.FETCH_PLAYER_STATS, attributeNodes = @NamedAttributeNode("playerStats"))}
)
public class Game implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Game.findAll";
    public static final String FETCH_SCORES = "Game.fetchScores";
    public static final String FETCH_SCORES_AND_PLAYER_STATS = "Game.fetchScoresAndPlayerStats";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column(name = "scheduled_tipoff")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledTipoff;

    @Basic
    @Column(name = "official_nbr")
    private String officialNbr;

    @Basic
    @Column
    private Integer attendance;

    @Basic
    @Column
    private String recap;

    @OneToMany(mappedBy = "game")
    @MapKeyColumn(name = "is_home")
    private Map<Boolean, Score> scores;

    public Game()
    {
    }

    public Game(Game g)
    {
        this(g.getScheduledTipoff(), g.getOfficialNbr(), g.getAttendance(), g.getRecap());

        this.id = Objects.requireNonNull(g.getId());
    }

    public Game(Date scheduledTipoff)
    {
        this(scheduledTipoff, null, null, null);
    }

    public Game(Date scheduledTipoff, String officialNbr, Integer attendance, String recap)
    {
        this.scheduledTipoff = scheduledTipoff;
        this.officialNbr = officialNbr;
        this.attendance = attendance;
        this.recap = recap;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getScheduledTipoff()
    {
        return scheduledTipoff;
    }

    public void setScheduledTipoff(Date scheduledTipoff)
    {
        this.scheduledTipoff = scheduledTipoff;
    }

    public String getOfficialNbr()
    {
        return officialNbr;
    }

    public void setOfficialNbr(String officialNbr)
    {
        this.officialNbr = officialNbr;
    }

    public Integer getAttendance()
    {
        return attendance;
    }

    public void setAttendance(Integer attendance)
    {
        this.attendance = attendance;
    }

    public String getRecap()
    {
        return recap;
    }

    public void setRecap(String recap)
    {
        this.recap = recap;
    }

    public Map<Boolean, Score> getScores()
    {
        return scores;
    }

    public void setScores(Map<Boolean, Score> scores)
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
        Game other = ( Game ) obj;
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
        return "[" + id + ", " + scheduledTipoff + ", " + officialNbr + ", " + attendance + ", " + recap + "]";
    }
}
