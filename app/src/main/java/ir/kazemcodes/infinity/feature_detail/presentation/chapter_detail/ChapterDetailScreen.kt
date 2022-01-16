package ir.kazemcodes.infinity.feature_detail.presentation.chapter_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import com.zhuinden.simplestackcomposeintegration.services.rememberService
import ir.kazemcodes.infinity.feature_detail.presentation.book_detail.Constants
import ir.kazemcodes.infinity.feature_activity.presentation.ReaderScreenKey
import ir.kazemcodes.infinity.core.presentation.reusable_composable.ErrorTextWithEmojis
import ir.kazemcodes.infinity.core.presentation.reusable_composable.TopAppBarTitle


@Composable
fun ChapterDetailScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel = rememberService<ChapterDetailViewModel>()
    val chapters = viewModel.state.value.chapters
    val book = viewModel.state.value.book
    val backStack = LocalBackstack.current
    val state = viewModel.state.value


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically,
                    ) {
                        TopAppBarTitle(title = "Content")
                    }
                },
                modifier = modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = Constants.DEFAULT_ELEVATION,
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(ChapterDetailEvent.ToggleOrder)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort Icon"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { backStack.goBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier.fillMaxSize()) {
            if (state.localChapters.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(count = state.localChapters.size) { index ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .height(40.dp)
                                .clickable {
                                    backStack.goTo(
                                        ReaderScreenKey(
                                            book = book,
                                            chapterIndex = viewModel.getIndexOfChapter(index),
                                            sourceName = viewModel.state.value.source.name,
                                            chapter = state.localChapters[index],
                                        )
                                    )
                                },
                            verticalAlignment = CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.localChapters[index].title,
                                color = if (state.localChapters[index].haveBeenRead) MaterialTheme.colors.onBackground.copy(
                                    alpha = .4f) else MaterialTheme.colors.onBackground,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(7f)
                            )
                            Text(modifier = Modifier.weight(2f),
                                text = state.localChapters[index].dateUploaded ?: "",
                                fontStyle = FontStyle.Italic,
                                color = if (state.localChapters[index].haveBeenRead) MaterialTheme.colors.onBackground.copy(
                                    alpha = .4f) else MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.caption
                            )
                            Spacer(modifier = modifier.width(20.dp))
                            Icon(
                                imageVector = Icons.Default.PublishedWithChanges,
                                contentDescription = "Cached",
                                tint = if (state.localChapters[index].content.joinToString(" , ").length > 10) MaterialTheme.colors.onBackground else MaterialTheme.colors.background,
                            )
                        }
                    }

                }
            }

            if (viewModel.state.value.error.isNotBlank()) {
                ErrorTextWithEmojis(error = state.error, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.Center))
            }
            if (viewModel.state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
