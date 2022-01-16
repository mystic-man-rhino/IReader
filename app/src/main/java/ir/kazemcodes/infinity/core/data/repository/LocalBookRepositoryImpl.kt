package ir.kazemcodes.infinity.core.data.repository

import ir.kazemcodes.infinity.core.data.local.dao.BookDao
import ir.kazemcodes.infinity.core.domain.models.BookEntity
import ir.kazemcodes.infinity.core.domain.repository.LocalBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalBookRepositoryImpl @Inject constructor(
    private val dao: BookDao,
) : LocalBookRepository {


    override fun getAllBooks(): Flow<List<BookEntity>> {
        return dao.getAllBooks()
    }

    override fun getInLibraryBooks(): Flow<List<BookEntity>> {
       return  dao.getInLibraryBooks()
    }

    override fun getBookById(bookId: Int): Flow<BookEntity> {
        return dao.getBookById(bookId)
    }

    override fun getBookByName(bookName: String): Flow<BookEntity> {
        return dao.getBookByName(bookName)
    }

    override suspend fun insertBook(bookEntity: BookEntity) {
        return dao.insertBook(bookEntity)
    }

    override suspend fun updateBook(bookEntity: BookEntity) {
        return dao.updateBook(bookEntity)
    }

    override suspend fun insertBooks(bookEntities: List<BookEntity>) {
        return dao.insertBooks(bookEntities)
    }

    override suspend fun deleteBook(bookName: String) {
        return dao.deleteBook(bookName = bookName)
    }

    override suspend fun deleteAllBook() {
        return dao.deleteAllBook()
    }

    override suspend fun deleteNotInLibraryBooks() {
        return dao.deleteAllNotInLibraryBooks()
    }
}