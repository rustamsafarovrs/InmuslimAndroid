package tj.rsdevteam.inmuslim.data.models.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tj.rsdevteam.inmuslim.data.models.Timing

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@JsonClass(generateAdapter = true)
data class GetTimingResponse(
    val result: Int,
    val msg: String,
    val region: String,
    @field:Json(name = "begin_date")
    val beginDate: String,
    val timing: Timing
)