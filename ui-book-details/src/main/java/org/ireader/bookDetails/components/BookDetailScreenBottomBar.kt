package org.ireader.bookDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ireader.common_resources.UiText
import org.ireader.components.components.ShowLoading
import org.ireader.core_ui.asString
import org.ireader.core_ui.ui.string
import org.ireader.core_ui.ui_components.ClickableTextIcon
import org.ireader.ui_book_details.R

@Composable
fun BookDetailScreenBottomBar(
    modifier: Modifier = Modifier,
    isInLibrary: Boolean,
    isInLibraryInProgress: Boolean,
    onToggleInLibrary: () -> Unit,
    onDownload: () -> Unit,
    isRead: Boolean,
    onRead: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 8.dp,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically

        ) {
            ClickableTextIcon(
                modifier = Modifier.weight(1F),
                text = if (!isInLibrary) UiText.StringResource(R.string.add_to_library) else UiText.StringResource(R.string.added_to_library),
                icon = {
                    if (isInLibraryInProgress) {
                        ShowLoading()
                    } else {
                        Icon(
                            imageVector = if (!isInLibrary) Icons.Default.AddCircleOutline else Icons.Default.Check,
                            contentDescription =  UiText.StringResource(R.string.toggle_in_library).asString(),
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                onClick = {
                    if (!isInLibraryInProgress) {
                        onToggleInLibrary()
                    }
                },
            )

            ClickableTextIcon(
                modifier = Modifier.weight(1F),
                text = if (isRead) UiText.StringResource(R.string.continue_reading) else UiText.StringResource(R.string.read),
                icon = {
                    Icon(
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = "Continue Reading",
                        tint = MaterialTheme.colors.onBackground
                    )
                },
                onClick = {
                    onRead()
                }
            )

            ClickableTextIcon(
                modifier = Modifier.weight(1F),
                text = UiText.StringResource( R.string.download),
                icon = {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        contentDescription = string(id = R.string.download),
                        tint = MaterialTheme.colors.onBackground
                    )
                },
                onClick = {
                    onDownload()
                }
            )
        }
    }
}
