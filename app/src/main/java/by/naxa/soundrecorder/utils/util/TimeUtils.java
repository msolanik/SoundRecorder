package by.naxa.soundrecorder.utils.util;

import android.util.Log;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String formatDuration(long millis) {
        if (millis < 0) {
            return "00:00";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.HOURS.toSeconds(hours)
                - TimeUnit.MINUTES.toSeconds(minutes);
        if (hours > 0) {
            return String.format(Locale.ENGLISH,  "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.ENGLISH,  "%02d:%02d", minutes, seconds);
        }
    }

}
