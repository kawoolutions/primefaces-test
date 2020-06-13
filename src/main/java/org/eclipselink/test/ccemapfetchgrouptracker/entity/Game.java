package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private LocalDateTime scheduledTipoff;

    @Basic
    @Column(name = "official_nbr")
    private String officialNbr;

    @Basic
    @Column
    private Integer attendance;

    @OneToMany(mappedBy = "game")
    @MapKeyColumn(name = "is_home")
    private Map<Boolean, Score> scores;

    public Game()
    {
    }

    public Game(Integer id)
    {
        this(id, null);
    }

    public Game(Integer id, LocalDateTime scheduledTipoff)
    {
        this(id, scheduledTipoff, null, null);
    }

    public Game(LocalDateTime scheduledTipoff, String officialNbr, Integer attendance)
    {
        this(null, scheduledTipoff, officialNbr, attendance);
    }

    public Game(Integer id, LocalDateTime scheduledTipoff, String officialNbr, Integer attendance)
    {
        this.id = Objects.requireNonNull(id);
        this.scheduledTipoff = scheduledTipoff;
        this.officialNbr = officialNbr;
        this.attendance = attendance;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public LocalDateTime getScheduledTipoff()
    {
        return scheduledTipoff;
    }

    public void setScheduledTipoff(LocalDateTime scheduledTipoff)
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
        return "[" + id + ", " + scheduledTipoff + ", " + officialNbr + ", " + attendance + "]";
    }
}
