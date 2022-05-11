package org.ireader.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ireader.common_models.SortType
import org.ireader.common_resources.UiText
import org.ireader.components.text_related.TextIcon

@Composable
fun SortScreen(
    sortType: SortType,
    isSortDesc: Boolean,
    onSortSelected: (SortType) -> Unit
) {
    val items = listOf<SortType>(
        SortType.Alphabetically,
        SortType.LastRead,
        SortType.LastChecked,
        SortType.TotalChapters,
        SortType.LatestChapter,
        SortType.DateFetched,
        SortType.DateAdded,
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            items.forEach { item ->

                TextIcon(
                    UiText.DynamicString(item.name),
                    if (isSortDesc) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                    sortType == item,
                    onClick = {
                        onSortSelected(item)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
