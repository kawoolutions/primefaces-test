package org.eclipselink.test.fetchgroupreportquery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

import org.eclipselink.test.fetchgroupreportquery.entity.SimpleGame;
import org.eclipselink.test.fetchgroupreportquery.entity.SimpleRoster;
import org.eclipselink.test.fetchgroupreportquery.entity.SimpleScore;

public class NamingUtils
{
    public static String getGameLabelFor( SimpleGame game )
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );
        LocalDateTime scheduledTipoff = game.getScheduledTipoff();
        String isoDate = scheduledTipoff.format( formatter );
        
        Map<Boolean, SimpleScore> scores = game.getSimpleScores();
        
        String label = isoDate + ": " + getTeamLabelForRoster( scores.get( Boolean.TRUE ).getSimpleRoster() ) + " vs. " + getTeamLabelForRoster( scores.get( Boolean.FALSE ).getSimpleRoster() );
        
        return label;
    }

    public static String getTeamLabelForRoster( SimpleRoster roster )
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
