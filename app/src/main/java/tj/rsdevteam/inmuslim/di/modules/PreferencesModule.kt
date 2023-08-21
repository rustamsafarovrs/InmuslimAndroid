package tj.rsdevteam.inmuslim.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tj.rsdevteam.inmuslim.data.preferences.Preferences

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    fun providePreferences(@ApplicationContext context: Context) = Preferences(context)
}
