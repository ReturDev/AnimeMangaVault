package github.returdev.animemangavault.data.cache.model.converter

import androidx.room.TypeConverter

/**
 * Class for converting between a list of strings and a comma-separated string representation.
 * Used as a Room database type converter.
 */
class IntListConverter {

    /**
     * Converts a comma-separated string into a list of strings.
     *
     * @param string The comma-separated string.
     * @return A list of strings.
     */
    @TypeConverter
    fun toList(string: String) = string.split(",")


    /**
     * Converts a list of strings into a comma-separated string.
     *
     * @param list The list of strings.
     * @return A comma-separated string.
     */
    @TypeConverter
    fun fromList(list : List<Int>) = list.joinToString(",")

}