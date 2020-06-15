package org.eclipselink.test.ccemapfetchgrouptracker.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@NamedQuery(name = Game.FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID, query = "SELECT ga FROM Game ga JOIN ga.scores sc GROUP BY ga.id")
@NamedEntityGraph(name = Game.FETCH_SCORES, attributeNodes = {@NamedAttributeNode("scores")})
@NamedEntityGraph(name = Game.FETCH_SCORES_AND_PLAYER_STATS,
                  attributeNodes = {@NamedAttributeNode(value = "scores", subgraph = Score.FETCH_PLAYER_STATS)},
                  subgraphs = {@NamedSubgraph(name = Score.FETCH_PLAYER_STATS, attributeNodes = @NamedAttributeNode("playerStats"))}
)
public class Game implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Game.findAll";
    public static final String FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID = "Game.findAllJoinScoresGroupByGameId";
    public static final String FETCH_SCORES = "Game.fetchScores";
    public static final String FETCH_SCORES_AND_PLAYER_STATS = "Game.fetchScoresAndPlayerStats";

    @Id
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column(name = "scheduled_tipoff")
    private LocalDateTime scheduledTipoff;

    @OneToMany(mappedBy = "game")
    @MapKeyColumn(name = "is_home", insertable = false, updatable = false)
    private Map<Boolean, Score> scores;

    public Game()
    {
    }

    public Game(Integer id)
    {
        this(id, null);
    }

    public Game(LocalDateTime scheduledTipoff)
    {
        this(null, scheduledTipoff);
    }

    public Game(Integer id, LocalDateTime scheduledTipoff)
    {
        this.id = id;
        this.scheduledTipoff = scheduledTipoff;
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
        return "[" + id + ", " + scheduledTipoff + "]";
    }
}
