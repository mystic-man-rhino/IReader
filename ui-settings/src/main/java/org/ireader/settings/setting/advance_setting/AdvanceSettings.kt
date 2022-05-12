package org.ireader.settings.setting.advance_setting

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.ireader.common_extensions.findComponentActivity
import org.ireader.common_extensions.getCacheSize
import org.ireader.common_extensions.launchIO
import org.ireader.common_models.BackUpBook
import org.ireader.common_resources.UiEvent
import org.ireader.common_resources.UiText
import org.ireader.components.components.ISnackBarHost
import org.ireader.components.components.Toolbar
import org.ireader.components.reusable_composable.BigSizeTextComposable
import org.ireader.components.reusable_composable.TopAppBarBackButton
import org.ireader.components.text_related.TextSection
import org.ireader.core_ui.component.PreferenceRow
import org.ireader.settings.setting.SettingViewModel
import org.ireader.ui_settings.R
import java.io.FileInputStream
import java.io.FileOutputStream

@OptIn(ExperimentalSerializationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdvanceSettings(
    modifier: Modifier = Modifier,
    vm: SettingViewModel,
    onBackStack: () -> Unit

) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val onBackup =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultIntent ->
            if (resultIntent.resultCode == Activity.RESULT_OK && resultIntent.data != null) {
                scope.launchIO {
                    try {
                        val contentResolver = context.findComponentActivity()!!.contentResolver
                        val uri = resultIntent.data!!.data!!
                        val pfd = contentResolver.openFileDescriptor(uri, "w")
                        pfd?.use {
                            FileOutputStream(pfd.fileDescriptor).use { outputStream ->
                                outputStream.write(vm.getAllBooks().toByteArray())
                            }
                        }
                    } catch (e: SerializationException) {
                        vm.showSnackBar(UiText.ExceptionString(e))
                    } catch (e: Throwable) {
                        vm.showSnackBar(UiText.ExceptionString(e))
                    }
                }
            }
        }

    val onRestore =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultIntent ->
            if (resultIntent.resultCode == Activity.RESULT_OK && resultIntent.data != null) {
                try {
                    scope.launchIO {
                        val contentResolver = context.findComponentActivity()!!.contentResolver
                        val uri = resultIntent.data!!.data!!
                        contentResolver
                            .takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            )
                        val pfd = contentResolver.openFileDescriptor(uri, "r")
                        pfd?.use {
                            FileInputStream(pfd.fileDescriptor).use { stream ->
                                val txt = stream.readBytes().decodeToString()
                                kotlin.runCatching {
                                    vm.insertBackup(
                                        Json.Default.decodeFromString<List<BackUpBook>>(
                                            txt
                                        )
                                    )
                                    vm.showSnackBar(UiText.StringResource(R.string.restoredSuccessfully))
                                }.getOrElse { e ->
                                    vm.showSnackBar(UiText.ExceptionString(e))
                                }
                            }
                        }
                    }
                } catch (e: Throwable) {
                    vm.showSnackBar(UiText.ExceptionString(e))
                }
            }
        }

    LaunchedEffect(key1 = true) {
        vm.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        event.uiText.asString(context)
                    )
                }
            }
        }
    }

    androidx.compose.material3.Scaffold(
        topBar = {
            Toolbar(
                title = {
                    BigSizeTextComposable(text =   UiText.StringResource(R.string.advance_setting))
                },
                navigationIcon = { TopAppBarBackButton(onClick = onBackStack) }
            )
        },
        snackbarHost = { ISnackBarHost(snackBarHostState = snackBarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            TextSection(text = UiText.StringResource(R.string.data), toUpper = false)
            PreferenceRow(title = stringResource(id = R.string.clear_all_database), onClick = {
                vm.deleteAllDatabase()
                vm.showSnackBar(
                    UiText.StringResource(R.string.database_was_cleared)
                )
            })
            PreferenceRow(title =  stringResource(R.string.clear_all_chapters), onClick = {
                vm.deleteAllChapters()
                vm.showSnackBar(UiText.StringResource(R.string.chapters_was_cleared))
            })
            PreferenceRow(
                title =   stringResource(R.string.clear_all_cache),
                subtitle =getCacheSize(context = context) ,
                onClick = {
                    context.cacheDir.deleteRecursively()
                    vm.showSnackBar(UiText.DynamicString("Clear was cleared."))
                }
            )

            TextSection(text =   UiText.StringResource(R.string.backup), toUpper = false)
            PreferenceRow(title =   stringResource(R.string.backup), onClick = {
                context.findComponentActivity()
                    ?.let { activity ->
                        vm.onLocalBackupRequested { intent: Intent ->
                            onBackup.launch(intent)
                        }
                    }
            })
            PreferenceRow(title =   stringResource(R.string.restore), onClick ={
                context.findComponentActivity()
                    ?.let { activity ->
                        vm.onRestoreBackupRequested { intent: Intent ->
                            onRestore.launch(intent)
                        }
                    }
            })
            TextSection(text =   UiText.StringResource(R.string.reset_setting), toUpper = false)
            PreferenceRow(title =   stringResource(R.string.reset_reader_screen_settings), onClick = {
                vm.deleteDefaultSettings()
            })
        }
    }
}
