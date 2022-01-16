package ir.kazemcodes.infinity.di

import android.content.Context
import android.content.SharedPreferences
import android.webkit.WebView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.kazemcodes.infinity.core.data.network.utils.MemoryCookieJar
import ir.kazemcodes.infinity.core.data.repository.PreferencesHelper
import ir.kazemcodes.infinity.core.domain.repository.Repository
import ir.kazemcodes.infinity.core.domain.use_cases.preferences.*
import ir.kazemcodes.infinity.core.domain.use_cases.remote.*
import ir.kazemcodes.infinity.feature_sources.sources.utils.NetworkHelper
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesRemoteUseCases(): RemoteUseCase {
        return RemoteUseCase(
            getRemoteBookDetailUseCase = GetRemoteBookDetailUseCase(),
            getRemoteLatestUpdateLatestBooksUseCase = GetRemoteLatestBooksUseCase(),
            getRemoteChaptersUseCase = GetRemoteChaptersUseCase(),
            getRemoteReadingContentUseCase = GetRemoteReadingContentUseCase(),
            getSearchedBooksUseCase = GetRemoteSearchBookUseCase(),
            getRemoteMostPopularBooksUseCase = GetRemoteMostPopularBooksUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreUseCase(repository: Repository): PreferencesUseCase {
        return PreferencesUseCase(
            readSelectedFontStateUseCase = ReadSelectedFontStateUseCase(repository),
            saveSelectedFontStateUseCase = SaveSelectedFontStateUseCase(repository),
            readFontSizeStateUseCase = ReadFontSizeStateUseCase(repository),
            saveFontSizeStateUseCase = SaveFontSizeStateUseCase(repository),
            readBrightnessStateUseCase = ReadBrightnessStateUseCase(repository),
            saveBrightnessStateUseCase = SaveBrightnessStateUseCase(repository),
            saveLibraryLayoutUseCase = SaveLibraryLayoutTypeStateUseCase(repository),
            readLibraryLayoutUseCase = ReadLibraryLayoutTypeStateUseCase(repository),
            saveBrowseLayoutUseCase = SaveBrowseLayoutTypeStateUseCase(repository),
            readBrowseLayoutUseCase = ReadBrowseLayoutTypeStateUseCase(repository),
            readDohPrefUseCase = ReadDohPrefUseCase(repository = repository),
            saveDohPrefUseCase = SaveDohPrefUseCase(repository),
            getBackgroundColorUseCase = GetBackgroundColorUseCase(repository),
            setBackgroundColorUseCase = SetBackgroundColorUseCase(repository = repository),
            readFontHeightUseCase = ReadFontHeightUseCase(repository),
            saveFontHeightUseCase = SaveFontHeightUseCase(repository),
            saveParagraphDistanceUseCase = SaveParagraphDistanceUseCase(repository),
            readParagraphDistanceUseCase = ReadParagraphDistanceUseCase(repository),
            readOrientationUseCase = ReadOrientationUseCase(repository),
            saveOrientationUseCase = SaveOrientationUseCase(repository),
            readParagraphIndentUseCase = ReadParagraphIndentUseCase(repository),
            saveParagraphIndentUseCase = SaveParagraphIndentUseCase(repository)
        )
    }
    @Singleton
    @Provides
    fun providesCookieJar(): CookieJar {
        return MemoryCookieJar()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(cookieJar: CookieJar): OkHttpClient {
        return OkHttpClient.Builder().apply {
            networkInterceptors().add(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BASIC)
                }
            )
            readTimeout(15, TimeUnit.SECONDS)
            connectTimeout(15, TimeUnit.SECONDS)
            cookieJar(cookieJar)

        }
            .build()
    }
    @Singleton
    @Provides
    fun providesNetworkHelper(context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Singleton
    @Provides
    fun providePreferenceHelper(sharedPreferences: SharedPreferences): PreferencesHelper {
        return PreferencesHelper(sharedPreferences)
    }

    @Singleton
    @Provides
    fun providesWebView(context: Context): WebView {
        return WebView(context)
    }


}