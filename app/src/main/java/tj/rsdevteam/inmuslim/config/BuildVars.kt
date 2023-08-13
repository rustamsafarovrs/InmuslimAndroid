package tj.rsdevteam.inmuslim.config

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */
object BuildVars {

    val BUILD_TYPE = BuildType.TEST

    val BASE_URL: String = when (BUILD_TYPE) {
        BuildType.TEST -> {
            "http://94.198.216.127:8088/staging/api/"
        }
        BuildType.PROD -> {
            "http://94.198.216.127/api"
        }
    }
}

enum class BuildType {
    TEST,
    PROD
}