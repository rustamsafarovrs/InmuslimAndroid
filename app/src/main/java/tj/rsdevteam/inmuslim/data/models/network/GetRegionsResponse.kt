package tj.rsdevteam.inmuslim.data.models.network

import com.squareup.moshi.JsonClass
import tj.rsdevteam.inmuslim.data.models.Region

/**
 * Created by Rustam Safarov on 14/08/23.
 * github.com/rustamsafarovrs
 */

@JsonClass(generateAdapter = true)
data class GetRegionsResponse(
    val result: Int,
    val msg: String,
    val regions: List<Region>
)
