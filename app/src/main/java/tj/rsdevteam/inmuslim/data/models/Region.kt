package tj.rsdevteam.inmuslim.data.models

import androidx.compose.runtime.mutableStateOf
import com.squareup.moshi.JsonClass

/**
 * Created by Rustam Safarov on 14/08/23.
 * github.com/rustamsafarovrs
 */

@JsonClass(generateAdapter = true)
data class Region(
    val id: Long,
    val name: String
) {

    @Transient
    var selected = mutableStateOf(false)
}