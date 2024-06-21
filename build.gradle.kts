import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.8" apply false
}

allprojects.onEach { project ->
    project.afterEvaluate {
        if (project.plugins.hasPlugin("org.jetbrains.kotlin.android")) {
            project.plugins.apply("io.gitlab.arturbosch.detekt")
            project.extensions.configure<DetektExtension> {
                config.setFrom(rootProject.files("config/detekt/detekt.yml"))
                baseline = rootProject.file("config/detekt/baseline.xml")
            }
            project.dependencies.add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
        }
    }
}
