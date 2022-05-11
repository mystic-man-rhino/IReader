package org.ireader.components.text_related

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import org.ireader.common_resources.UiText
import org.ireader.components.reusable_composable.MidSizeTextComposable
import org.ireader.components.reusable_composable.SuperSmallTextComposable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdvanceSettingItem(
    title: UiText = UiText.DynamicString(""),
    subtitle: UiText =  UiText.DynamicString(""),
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() },
        text = {
            MidSizeTextComposable(text = title)
        },
        secondaryText = {
            SuperSmallTextComposable(text = subtitle)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AdvanceSettingPrev() {
    AdvanceSettingItem(title = UiText.DynamicString("Clear All Table"), subtitle = UiText.DynamicString("38.6 kb"))
}
