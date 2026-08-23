import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
    implementation(libs.compose.components.resources)
}

compose.desktop {
    application {
        mainClass = "band.effective.education.crossplatform.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "band.effective.education.crossplatform"
            packageVersion = "1.0.0"
        }
    }
}
// Задача run создаётся плагином Compose уже после разбора скрипта,
// поэтому настраиваем её через withType, а не по имени.
tasks.withType<JavaExec>().configureEach {
    if (name != "run") return@configureEach
    // Проверить вторую локаль, не меняя язык всей системы:
    //   ./gradlew :desktopApp:run -Plocale=en
    //
    // Дописываем в doFirst, а не сразу: плагин Compose подставляет свой
    // -Duser.language уже после разбора скрипта, и наш аргумент он бы затёр.
    // У JVM выигрывает последний -D с тем же ключом.
    val locale = providers.gradleProperty("locale").orNull
    if (locale != null) {
        doFirst { jvmArgs("-Duser.language=$locale", "-Duser.country=") }
    }
}
