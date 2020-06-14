package org.eclipselink.test.ccemapfetchgrouptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

import org.eclipselink.test.ccemapfetchgrouptracker.entity.Game;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Roster;
import org.eclipselink.test.ccemapfetchgrouptracker.entity.Score;

public class NamingUtils
{
    public static String getGameLabelFor( Game game )
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );
        LocalDateTime scheduledTipoff = game.getScheduledTipoff();
        String isoDate = scheduledTipoff.format( formatter );
        
        Map<Boolean, Score> scores = game.getScores();
        
        String label = isoDate + ": " + getTeamLabelForRoster( scores.get( Boolean.TRUE ).getRoster() ) + " vs. " + getTeamLabelForRoster( scores.get( Boolean.FALSE ).getRoster() );
        
        return label;
    }

    public static String getTeamLabelForRoster( Roster roster )
    {
        if ( roster == null )
        {
            return "Null EAGER roster!";
        }
        
        return getTeamLabelFor( roster.getClubName(), roster.getOrdinalNbr() );
    }
    
    public static String getTeamLabelFor( String clubName, Integer teamNumber )
    {
        Objects.requireNonNull( clubName, "Club name is null!" );
        
        if ( clubName.isEmpty() )
        {
            throw new IllegalArgumentException( "Club name is empty!" );
        }
        
        if ( teamNumber <= 0 )
        {
            throw new IllegalArgumentException( "Team number is zero or less!" );
        }
        
        // setting only last or first name leaves off comma
        String teamName = clubName + " " + teamNumber;
        
        return teamName;
    }
}
