package github.returdev.animemangavault.data.library.model.converter

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter for converting between Long and Date objects.
 */
class DateConverter {

    /**
     * Converts a Long value to a Date object.
     *
     * @param value The Long value representing the time in milliseconds.
     * @return A Date object representing the time.
     */
    @TypeConverter
    fun toDate(value: Long) : Date {
        return Date(value)
    }

    /**
     * Converts a Date object to a Long value.
     *
     * @param value The Date object to be converted.
     * @return The Long value representing the time in milliseconds.
     */
    @TypeConverter
    fun fromDate(value: Date) : Long{
        return value.time
    }

}