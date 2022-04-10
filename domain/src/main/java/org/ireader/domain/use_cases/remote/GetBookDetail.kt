package org.ireader.domain.use_cases.remote

import kotlinx.coroutines.CancellationException
import org.ireader.core.utils.UiText
import org.ireader.core.utils.exceptionHandler
import org.ireader.domain.models.entities.Book
import org.ireader.domain.models.entities.Book.Companion.toBookInfo
import org.ireader.domain.models.entities.toBook
import org.ireader.domain.models.entities.updateBook
import tachiyomi.source.Source
import timber.log.Timber
import javax.inject.Inject

class GetBookDetail @Inject constructor() {
    suspend operator fun invoke(
        book: Book,
        source: Source,
        onError: suspend (UiText?) -> Unit,
        onSuccess: suspend (Book) -> Unit,
    ) {
        try {
            Timber.d("Timber: Remote Book Detail for ${book.title} Was called")
            val bookDetail = source.getMangaDetails(book.toBookInfo(source.id))
                .toBook(source.id)
            onSuccess(updateBook(bookDetail, book))
        } catch (e: CancellationException) {
        } catch (e: Exception) {
            onError(exceptionHandler(e))
        }
    }
}