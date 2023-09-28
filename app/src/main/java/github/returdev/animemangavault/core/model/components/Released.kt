package github.returdev.animemangavault.core.model.components

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

/**
 * Data class representing the release date information.
 *
 * @property from The starting date of the release.
 * @property to The ending date of the release (nullable).
 */
data class Released(
    val from : Calendar,
    val to : Calendar?
){

    override fun toString(): String {
        val dateFormat = DateFormat.getPatternInstance("dd/mm/yyyy HH:mm", Locale.getDefault())

        val fromFormatted = dateFormat.format(from)
        val toFormatted = to?.let { dateFormat.format(it) } ?: "??/??/??"

        return "$fromFormatted - $toFormatted"
    }
}