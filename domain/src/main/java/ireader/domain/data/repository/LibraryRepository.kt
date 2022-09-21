package ireader.domain.data.repository

import kotlinx.coroutines.flow.Flow
import ireader.common.models.entities.Book
import ireader.common.models.entities.LibraryBook
import ireader.common.models.library.LibrarySort

interface LibraryRepository {

    suspend fun findAll(sort: LibrarySort): List<LibraryBook>
    fun subscribe(sort: LibrarySort): Flow<List<LibraryBook>>

    suspend fun findDownloadedBooks(): List<Book>

    suspend fun findFavorites(): List<Book>
}