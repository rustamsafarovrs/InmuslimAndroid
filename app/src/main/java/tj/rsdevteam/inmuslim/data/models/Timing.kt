package tj.rsdevteam.inmuslim.data.models

import com.squareup.moshi.JsonClass

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@JsonClass(generateAdapter = true)
data class Timing(
    val fajr: String,
    val sunrise: String,
    val zuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String
)
