package org.ireader.components.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.ireader.common_resources.UiText
import org.ireader.components.reusable_composable.TopAppBarBackButton
import org.ireader.core_ui.ui.EmptyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyScreenComposable(errorResId: Int,onPopBackStack :() -> Unit) {
    Scaffold(
        topBar = {
            Toolbar(
                title = {},
                navigationIcon = { TopAppBarBackButton(onClick = onPopBackStack) },
            )
        }
    ) { padding ->
        EmptyScreen(text = UiText.StringResource(errorResId), modifier = Modifier.padding(padding))
    }
}
