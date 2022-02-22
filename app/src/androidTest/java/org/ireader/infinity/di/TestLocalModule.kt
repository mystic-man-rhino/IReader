package org.ireader.infinity.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.ireader.core.prefs.AndroidPreferenceStore
import org.ireader.core.prefs.PreferenceStore
import org.ireader.data.local.AppDatabase
import org.ireader.data.local.dao.DownloadDao
import org.ireader.data.local.dao.LibraryBookDao
import org.ireader.data.local.dao.LibraryChapterDao
import org.ireader.data.local.dao.RemoteKeysDao
import org.ireader.data.repository.DownloadRepositoryImpl
import org.ireader.domain.repository.DownloadRepository
import org.ireader.domain.repository.LocalBookRepository
import org.ireader.domain.repository.LocalChapterRepository
import org.ireader.domain.source.Extensions
import org.ireader.domain.ui.AppPreferences
import org.ireader.domain.ui.UiPreferences
import org.ireader.domain.use_cases.download.DownloadUseCases
import org.ireader.domain.use_cases.download.delete.DeleteAllSavedDownload
import org.ireader.domain.use_cases.download.delete.DeleteSavedDownload
import org.ireader.domain.use_cases.download.delete.DeleteSavedDownloadByBookId
import org.ireader.domain.use_cases.download.get.GetAllDownloadsUseCase
import org.ireader.domain.use_cases.download.get.GetAllDownloadsUseCaseByPaging
import org.ireader.domain.use_cases.download.get.GetOneSavedDownload
import org.ireader.domain.use_cases.download.insert.InsertDownload
import org.ireader.domain.use_cases.download.insert.InsertDownloads
import org.ireader.domain.use_cases.local.DeleteUseCase
import org.ireader.domain.use_cases.local.LocalGetBookUseCases
import org.ireader.domain.use_cases.local.LocalGetChapterUseCase
import org.ireader.domain.use_cases.local.LocalInsertUseCases
import org.ireader.domain.use_cases.local.book_usecases.*
import org.ireader.domain.use_cases.local.chapter_usecases.*
import org.ireader.domain.use_cases.local.delete_usecases.book.DeleteAllBooks
import org.ireader.domain.use_cases.local.delete_usecases.book.DeleteAllExploreBook
import org.ireader.domain.use_cases.local.delete_usecases.book.DeleteBookById
import org.ireader.domain.use_cases.local.delete_usecases.book.DeleteNotInLibraryBook
import org.ireader.domain.use_cases.local.delete_usecases.chapter.*
import org.ireader.domain.use_cases.local.insert_usecases.InsertBook
import org.ireader.domain.use_cases.local.insert_usecases.InsertBooks
import org.ireader.domain.use_cases.local.insert_usecases.InsertChapter
import org.ireader.domain.use_cases.local.insert_usecases.InsertChapters
import org.ireader.infinity.core.domain.use_cases.local.book_usecases.GetBooksByQueryPagingSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class TestLocalModule {


    @Provides
    @Singleton
    fun provideBookDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            appContext,
            AppDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun providesBookDao(db: AppDatabase): LibraryBookDao {
        return db.libraryBookDao
    }


    @Provides
    @Singleton
    fun provideChapterDao(db: AppDatabase): LibraryChapterDao {
        return db.libraryChapterDao
    }

    @Provides
    @Singleton
    fun provideRemoteKeyDao(db: AppDatabase): RemoteKeysDao {
        return db.remoteKeysDao
    }


    @Singleton
    @Provides
    fun providesExtensions(@ApplicationContext context: Context): Extensions {
        return Extensions(context)
    }

    @Singleton
    @Provides
    fun providesDeleteUseCase(
        localBookRepository: LocalBookRepository,
        localChapterRepository: LocalChapterRepository,
    ): DeleteUseCase {
        return DeleteUseCase(
            deleteAllBook = DeleteAllBooks(localBookRepository),
            deleteAllExploreBook = DeleteAllExploreBook(localBookRepository),
            deleteBookById = DeleteBookById(localBookRepository),
            deleteNotInLibraryBook = DeleteNotInLibraryBook(localBookRepository),
            deleteChapterByChapter = DeleteChapterByChapter(localChapterRepository),
            deleteChaptersByBookId = DeleteChaptersByBookId(localChapterRepository),
            deleteNotInLibraryChapters = DeleteNotInLibraryChapters(localChapterRepository),
            deleteAllChapters = DeleteAllChapters(localChapterRepository),
            deleteChapters = DeleteChapters(localChapterRepository = localChapterRepository)
        )
    }

    @Singleton
    @Provides
    fun provideDownloadUseCase(
        downloadRepository: DownloadRepository,
    ): DownloadUseCases {
        return DownloadUseCases(
            deleteAllSavedDownload = DeleteAllSavedDownload(downloadRepository),
            deleteSavedDownload = DeleteSavedDownload(downloadRepository),
            deleteSavedDownloadByBookId = DeleteSavedDownloadByBookId(downloadRepository),
            getAllDownloadsUseCase = GetAllDownloadsUseCase(downloadRepository),
            getOneSavedDownload = GetOneSavedDownload(downloadRepository),
            insertDownload = InsertDownload(downloadRepository),
            insertDownloads = InsertDownloads(downloadRepository),
            getAllDownloadsUseCaseByPaging = GetAllDownloadsUseCaseByPaging(downloadRepository)
        )
    }

    @Singleton
    @Provides
    fun provideDownloadRepository(
        downloadDao: DownloadDao,
    ): DownloadRepository {
        return DownloadRepositoryImpl(downloadDao)
    }

    @Singleton
    @Provides
    fun provideDownloadDao(
        database: AppDatabase,
    ): DownloadDao {
        return database.downloadDao
    }


    @Singleton
    @Provides
    fun providesInsertUseCase(
        localBookRepository: LocalBookRepository,
        localChapterRepository: LocalChapterRepository,
    ): LocalInsertUseCases {
        return LocalInsertUseCases(
            insertBook = InsertBook(localBookRepository),
            insertBooks = InsertBooks(localBookRepository),
            insertChapter = InsertChapter(localChapterRepository),
            insertChapters = InsertChapters(localChapterRepository),
            setLastReadToFalse = SetLastReadToFalse(localChapterRepository)
        )
    }

    @Singleton
    @Provides
    fun providesGetBookUseCase(
        localBookRepository: LocalBookRepository,
    ): LocalGetBookUseCases {
        return LocalGetBookUseCases(
            subscribeAllInLibraryBooks = SubscribeAllInLibraryBooks(
                localBookRepository),
            getAllExploredBookPagingSource = GetAllExploredBookPagingSource(localBookRepository),
            getAllInLibraryPagingSource = GetAllInLibraryPagingSource(
                localBookRepository),
            subscribeBookById = SubscribeBookById(localBookRepository),
            getBooksByQueryByPagination = GetBooksByQueryByPagination(localBookRepository),
            getBooksByQueryPagingSource = GetBooksByQueryPagingSource(localBookRepository),
            SubscribeInLibraryBooksPagingData = SubscribeInLibraryBooksPagingData(
                localBookRepository),
            getAllExploredBookPagingData = GetAllExploredBookPagingData(localBookRepository = localBookRepository),
        )
    }

    @Singleton
    @Provides
    fun providesGetChapterUseCase(
        localChapterRepository: LocalChapterRepository,
    ): LocalGetChapterUseCase {
        return LocalGetChapterUseCase(
            subscribeChapterById = SubscribeChapterById(localChapterRepository),
            subscribeChaptersByBookId = SubscribeChaptersByBookId(localChapterRepository),
            subscribeLastReadChapter = SubscribeLastReadChapter(localChapterRepository),
            getLocalChaptersByPaging = GetLocalChaptersByPaging(localChapterRepository),
            findFirstChapter = FindFirstChapter(localChapterRepository)
        )
    }

    @Provides
    @Singleton
    fun providePreferences(
        preferences: PreferenceStore,
    ): AppPreferences {
        return AppPreferences(preferences)
    }

    @Provides
    @Singleton
    fun provideUiPreferences(
        preferences: PreferenceStore,
    ): UiPreferences {
        return UiPreferences(preferences)
    }

    @Provides
    @Singleton
    fun providePreferencesStore(@ApplicationContext context: Context): PreferenceStore {
        return AndroidPreferenceStore(context = context, "ui")
    }
}