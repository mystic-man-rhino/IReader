package org.ireader.bookDetails.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import org.ireader.common_models.entities.Book
import org.ireader.image_loader.BookCover

@Composable
fun BoxScope.BookHeaderImage(
    book: Book
) {
    val backdropGradientColors = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.background,
    )
    AsyncImage(
        model = BookCover.from(book),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter,
        modifier = Modifier
            .matchParentSize()
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors = backdropGradientColors),
                )
            }
            .alpha(.2f),
    )
}