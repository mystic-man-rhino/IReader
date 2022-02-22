object Compose {
    const val composeVersion = "1.2.0-alpha02"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val icons = "androidx.compose.material:material-icons-extended:${composeVersion}"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeVersion"
    const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
    const val paging = "androidx.paging:paging-compose:1.0.0-alpha14"

    private const val navigationVersion = "2.5.0-alpha02"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.5.0-alpha02"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.5.0-alpha02"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"


    const val animations = "androidx.compose.animation:animation:1.2.0-alpha03"

    const val testing = "androidx.compose.ui:ui-test-junit4:${composeVersion}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Compose.composeVersion}"
    const val ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}