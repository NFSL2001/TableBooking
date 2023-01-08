package wia2007.project.tablebooking.converter;

import androidx.room.TypeConverter;

import java.sql.Time;

public class TimeConverter {
    @TypeConverter
    public static Time fromTimestampToTime(Long value) {
        return value == null ? null : new Time(value);
    }

    @TypeConverter
    public static Long timeToTimestamp(Time time) {
        return time == null ? null : time.getTime();
    }
}