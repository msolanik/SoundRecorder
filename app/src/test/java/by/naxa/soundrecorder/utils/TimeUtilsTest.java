package by.naxa.soundrecorder.utils;

import org.junit.Test;

import by.naxa.soundrecorder.utils.util.TimeUtils;

import static org.junit.Assert.assertEquals;

public class TimeUtilsTest {

    @Test
    public void formatDurationTest() {
        String time = TimeUtils.formatDuration(1000);
        assertEquals("00:01", time);
    }

    @Test
    public void formatDurationTest_negativeInput() {
        String time = TimeUtils.formatDuration(-1000);
        assertEquals("00:00", time);
    }

    @Test
    public void formatDurationTest_hourInput() {
        String time = TimeUtils.formatDuration(3600000);
        assertEquals("1:00:00", time);
    }
}

