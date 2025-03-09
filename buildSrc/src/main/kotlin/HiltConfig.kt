import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.hilt() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    pluginManager.apply("kotlin-kapt")
    pluginManager.apply("dagger.hilt.android.plugin")

    dependencies {
        add("implementation", libs.findLibrary("hilt.android").get())
        add("kapt", libs.findLibrary("hilt.android.compiler").get())
    }
}