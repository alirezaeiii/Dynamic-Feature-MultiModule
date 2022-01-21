import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.addTestsDependencies
import extensions.implementation

plugins {
    id("commons.android-library")
}

android {
    defaultConfig {
        buildConfigField("String", "API_BASE_URL", "\"https://content.viaplay.se/\"")
        buildConfigField("String", "DATABASE_NAME", "\"app-db\"")
        buildConfigField("int", "DATABASE_VERSION", "1")
        buildConfigField("boolean", "DATABASE_EXPORT_SCHEMA", "false")
    }
}

dependencies {
    api(project(BuildModules.COMMON))

    api(Dependencies.LIFECYCLE_EXTENSIONS)
    api(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_RUNTIME)
    api(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.MOSHI)
    implementation(Dependencies.MOSHI_KTX)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.DAGGER)

    kapt(AnnotationProcessorsDependencies.ROOM)
    kapt(AnnotationProcessorsDependencies.DAGGER)

    addTestsDependencies()
}