package ir.kazemcodes.infinity.library_feature.domain.use_case.chapter

import ir.kazemcodes.infinity.base_feature.repository.Repository
import ir.kazemcodes.infinity.core.Resource
import ir.kazemcodes.infinity.explore_feature.data.model.Chapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalChaptersByBookNameUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(bookName: String): Flow<Resource<List<Chapter>>> =
        flow {
            try {
                emit(Resource.Loading())
                repository.localChapterRepository.getChapterByName(bookName = bookName)
                    .collect { chapters ->
                        emit(Resource.Success<List<Chapter>>(data = chapters.map { chapterEntity ->
                            chapterEntity.toChapter()
                        }))
                    }
            } catch (e: Exception) {
                emit(Resource.Error<List<Chapter>>(message = e.message.toString()))
            }
        }


}