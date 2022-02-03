package ir.kazemcodes.infinity.feature_settings.presentation.appearance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.kazemcodes.infinity.core.presentation.reusable_composable.MidSizeTextComposable
import ir.kazemcodes.infinity.core.presentation.reusable_composable.TopAppBarBackButton
import ir.kazemcodes.infinity.core.presentation.reusable_composable.TopAppBarTitle
import ir.kazemcodes.infinity.core.utils.Constants
import ir.kazemcodes.infinity.feature_activity.presentation.AppTheme
import ir.kazemcodes.infinity.feature_activity.presentation.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppearanceSettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val openDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current


    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    TopAppBarTitle(title = "Appearance")
                },
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = Constants.DEFAULT_ELEVATION,
                navigationIcon = {
                    TopAppBarBackButton(navController = navController)
                }
            )
        }
    ) {
        ListItem(modifier = Modifier.clickable {
            openDialog.value = true
        }) {
            Row() {
                Icon(imageVector = Icons.Default.ModeNight,
                    contentDescription = "Night Mode",
                    tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(16.dp))
                MidSizeTextComposable(title = "Dark Mode")
            }

        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    TopAppBarTitle(title = "Night Mode")
                },
                buttons = {
                    Column(modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        val items = listOf(
                            AppearanceItems.Day,
                            AppearanceItems.Night,
                            AppearanceItems.Auto,
                        )
                        items.forEach {item ->
                            TextButton(onClick = {
                                viewModel.saveNightModePreferences(item.appTheme)
                                openDialog.value = false
                            }) {
                                MidSizeTextComposable(modifier = modifier.fillMaxWidth(),
                                    title = item.text,
                                    align = TextAlign.Start)
                            }
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
            )
        }


    }
}

sealed class AppearanceItems(val text: String,val appTheme: AppTheme) {
    object Day : AppearanceItems("Off",AppTheme.MODE_DAY)
    object Night : AppearanceItems("On",AppTheme.MODE_NIGHT)
    object Auto : AppearanceItems("Auto",AppTheme.MODE_AUTO)
}