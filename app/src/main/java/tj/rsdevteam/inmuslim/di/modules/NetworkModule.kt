package tj.rsdevteam.inmuslim.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tj.rsdevteam.inmuslim.config.BuildType
import tj.rsdevteam.inmuslim.config.BuildVars
import tj.rsdevteam.inmuslim.data.api.Api
import tj.rsdevteam.inmuslim.data.constants.Constants
import tj.rsdevteam.inmuslim.data.repositories.ErrorHandler
import java.util.concurrent.TimeUnit

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildVars.BUILD_TYPE == BuildType.TEST) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun provideOkhttp(
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(BuildVars.BASE_URL)
        .build()

    @Provides
    fun provideErrorHandler(moshi: Moshi) = ErrorHandler(moshi)

    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}
