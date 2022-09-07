plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}
android {
    namespace = "ireader.ui.settings"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeLib.versions.compiler.get()
    }
    androidComponents.onVariants { variant ->
        val name = variant.name
        sourceSets {
            getByName(name).kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/${name}/kotlin")
        }
    }
}

dependencies {
    implementation(project(Modules.commonModels))
    implementation(project(Modules.coreUi))

    implementation(project(Modules.coreApi))
    implementation(project(Modules.uiComponents))
    implementation(project(Modules.uiImageLoader))
    implementation(project(Modules.commonExtensions))
    implementation(project(Modules.commonResources))
    implementation(project(Modules.commonData))

    implementation(composeLib.compose.icons)
    implementation(composeLib.compose.googlFonts)
    implementation(composeLib.compose.navigation)
    implementation(composeLib.compose.foundation)
    implementation(composeLib.compose.animations)
    implementation(composeLib.material3.core)

    implementation(composeLib.compose.uiToolingPreview)
    implementation(accompanist.pagerIndicator)
    implementation(accompanist.pager)
    implementation(accompanist.swipeRefresh)

    implementation(libs.modernstorage.permissions)
    implementation(libs.modernstorage.storage)

    implementation(project(mapOf("path" to ":domain")))


    ksp(libs.koin.kspCompiler)
    implementation(libs.koin.androidCompose)
    compileOnly(libs.koin.annotations)
}
