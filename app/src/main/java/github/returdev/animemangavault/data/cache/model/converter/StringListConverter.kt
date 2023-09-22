package github.returdev.animemangavault.data.cache.model.converter

import androidx.room.TypeConverter

/**
 * Type converter for converting lists of strings to a single comma-separated string
 * and vice versa.
 */
class StringListConverter {

    /**
     * Converts a comma-separated string to a list of strings.
     *
     * @param string The comma-separated string to convert.
     * @return A list of strings.
     */
    @TypeConverter
    fun toList(string: String) = string.split(",")

    /**
     * Converts a list of strings to a single comma-separated string.
     *
     * @param list The list of strings to convert.
     * @return A single comma-separated string.
     */
    @TypeConverter
    fun fromList(list : List<String>) = list.joinToString(",")

}