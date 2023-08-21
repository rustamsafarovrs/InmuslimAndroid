package tj.rsdevteam.inmuslim.config

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */
object BuildVars {

    val BUILD_TYPE = BuildType.TEST

    val BASE_URL: String = when (BUILD_TYPE) {
        BuildType.TEST -> {
            "http://rsdevteam.ru:8088/inmuslim/staging/api/"
        }
        BuildType.PROD -> {
            "http://rsdevteam.ru/inmuslim/api"
        }
    }
}

enum class BuildType {
    TEST,
    PROD
}