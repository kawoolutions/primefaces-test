package org.eclipselink.test.fetchgroupreportquery.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"SimpleGames\"")
@NamedQuery(name = SimpleGame.FIND_ALL, query = "SELECT ga FROM SimpleGame ga")
@NamedQuery(name = SimpleGame.FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID, query = "SELECT ga FROM SimpleGame ga JOIN ga.simpleScores sc GROUP BY ga.id")
@NamedEntityGraph(name = SimpleGame.FETCH_SCORES, attributeNodes = {@NamedAttributeNode("simpleScores")})
public class SimpleGame implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "SimpleGame.findAll";
    public static final String FIND_ALL_JOIN_SCORES_GROUP_BY_GAME_ID = "SimpleGame.findAllJoinScoresGroupByGameId";
    public static final String FETCH_SCORES = "SimpleGame.fetchScores";

    @Id
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column(name = "scheduled_tipoff")
    private LocalDateTime scheduledTipoff;

    @OneToMany(mappedBy = "simpleGame")
    @MapKeyColumn(name = "is_home", insertable = false, updatable = false)
    private Map<Boolean, SimpleScore> simpleScores;

    public SimpleGame()
    {
    }

    public SimpleGame(Integer id)
    {
        this(id, null);
    }

    public SimpleGame(LocalDateTime scheduledTipoff)
    {
        this(null, scheduledTipoff);
    }

    public SimpleGame(Integer id, LocalDateTime scheduledTipoff)
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

    public Map<Boolean, SimpleScore> getSimpleScores()
    {
        return simpleScores;
    }

    public void setSimpleScores(Map<Boolean, SimpleScore> simpleScores)
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
        SimpleGame other = ( SimpleGame ) obj;
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
