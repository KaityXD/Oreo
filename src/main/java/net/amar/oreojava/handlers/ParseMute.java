package net.amar.oreojava.handlers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.TimeUnit;

public class ParseMute {
    public static boolean mute(User u, int amount, String duration, Guild g, String reason) {
        try {
            g.timeoutFor(u, amount, parseTimeUnit(duration)).reason(reason).queue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static TimeUnit parseTimeUnit(String duration) {
        if (duration == null || duration.isBlank())
            throw new IllegalArgumentException("Duration is empty");

        String unit = duration.replaceAll("\\d+", "").toLowerCase();

        return switch (unit) {
            case "w", "week", "weeks" -> TimeUnit.DAYS;   // handle weeks elsewhere (×7)
            case "d", "day", "days" -> TimeUnit.DAYS;
            case "h", "hour", "hours" -> TimeUnit.HOURS;
            case "m", "min", "minute", "minutes" -> TimeUnit.MINUTES;
            case "s", "sec", "second", "seconds" -> TimeUnit.SECONDS;
            default -> throw new IllegalArgumentException("Invalid time unit: " + unit);
        };
    }
}
