package ir.kazemcodes.infinity.core.domain.use_cases.local.chapter

import ir.kazemcodes.infinity.core.domain.models.Chapter
import ir.kazemcodes.infinity.core.domain.repository.Repository
import ir.kazemcodes.infinity.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetLocalChapterReadingContentUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(chapter: Chapter, source: String): Flow<Resource<Chapter?>> =
        flow {
            try {
                emit(Resource.Loading())
                Timber.d("Timber: GetLocalChapterReadingContentUseCase was Called")
                repository.localChapterRepository.getChapterByChapter(chapterTitle = chapter.title,
                    bookName = chapter.bookName ?: "",source)
                    .first { chapter ->
                        emit(Resource.Success<Chapter?>(data = chapter?.toChapter()))
                        return@first chapter != null
                    }
                Timber.d("GetLocalChapterReadingContentUseCase was Finished Successfully")
            } catch (e: Exception) {
                emit(Resource.Error<Chapter?>(message = e.message.toString()))
            }
        }


}