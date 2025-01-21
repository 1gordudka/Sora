package video.generator.ai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import video.generator.ai.data.local.GenerationDao
import video.generator.ai.data.local.GenerationDatabase
import video.generator.ai.data.network.api.BASE_URL
import video.generator.ai.data.network.api.MainApi
import video.generator.ai.data.network.server.SERVER_URL
import video.generator.ai.data.network.server.ServerApi
import video.generator.ai.data.preferences.UserPreferencesRepository
import java.util.concurrent.TimeUnit

private const val LAYOUT_PREFERENCE_NAME = "app_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMainApi() : MainApi{

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(MainApi::class.java)
    }

    @Provides
    fun provideServerApi() : ServerApi{

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ServerApi::class.java)
    }

    @Provides
    fun provideGenerationDao(@ApplicationContext context: Context) : GenerationDao{

        val database = Room.databaseBuilder(
            context,
            GenerationDatabase::class.java,
            "generation_database"
        ).build()
        return database.generationDao()
    }
    @Provides
    fun provideUserPreferencesRepository(@ApplicationContext context: Context) : UserPreferencesRepository {
        return UserPreferencesRepository(dataStore = context.dataStore)
    }
}