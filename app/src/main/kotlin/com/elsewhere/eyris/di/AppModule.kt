package com.elsewhere.eyris.di

import android.content.Context
import com.elsewhere.eyris.data.local.room.AppDatabase
import com.elsewhere.eyris.data.local.room.dao.BusinessDao
import com.elsewhere.eyris.data.remote.foursquare.FoursquareClient
import com.elsewhere.eyris.data.remote.osm.OsmClient
import com.elsewhere.eyris.data.remote.google.GoogleMapsScraper
import com.elsewhere.eyris.data.remote.firebase.FirebaseAuthManager
import com.elsewhere.eyris.data.remote.firebase.FirestoreManager
import com.elsewhere.eyris.data.remote.firebase.SyncManager
import com.elsewhere.eyris.data.repository.BusinessRepositoryImpl
import com.elsewhere.eyris.domain.repository.BusinessRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideBusinessDao(database: AppDatabase): BusinessDao {
        return database.businessDao()
    }

    @Singleton
    @Provides
    fun provideFoursquareClient(httpClient: HttpClient): FoursquareClient {
        return FoursquareClient(httpClient)
    }

    @Singleton
    @Provides
    fun provideOsmClient(httpClient: HttpClient): OsmClient {
        return OsmClient(httpClient)
    }

    @Singleton
    @Provides
    fun provideGoogleMapsScraper(httpClient: HttpClient): GoogleMapsScraper {
        return GoogleMapsScraper(httpClient)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthManager(firebaseAuth: FirebaseAuth): FirebaseAuthManager {
        return FirebaseAuthManager(firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideFirestoreManager(firestore: FirebaseFirestore): FirestoreManager {
        return FirestoreManager(firestore)
    }

    @Singleton
    @Provides
    fun provideSyncManager(@ApplicationContext context: Context): SyncManager {
        return SyncManager(context)
    }

    @Singleton
    @Provides
    fun provideBusinessRepository(
        businessDao: BusinessDao,
        foursquareClient: FoursquareClient,
        osmClient: OsmClient,
        googleMapsScraper: GoogleMapsScraper,
        firebaseAuthManager: FirebaseAuthManager,
        firestoreManager: FirestoreManager
    ): BusinessRepository {
        return BusinessRepositoryImpl(
            businessDao,
            foursquareClient,
            osmClient,
            googleMapsScraper,
            firebaseAuthManager,
            firestoreManager
        )
    }
}
