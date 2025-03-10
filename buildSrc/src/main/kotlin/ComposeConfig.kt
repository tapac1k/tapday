import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.plugins
import org.gradle.kotlin.dsl.getByType

enum class Target {
    CONTRACT, PRESENTATION, UTILS
}

fun Project.configureComposeDependencies(
    target: Target = Target.PRESENTATION
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    libs.findPlugin("kotlin.compose").get().get().let {
        pluginManager.apply(it.pluginId)
    }
    dependencies {
        libs.findLibrary("androidx.activity.compose").get().let { add("implementation", it) }
        libs.findLibrary("androidx.compose.bom").get().let { add("implementation", platform(it)) }

        if (target == Target.CONTRACT) return@dependencies

        libs.findLibrary("androidx.hilt.navigation.compose").get().let { add("implementation", it) }
        libs.findLibrary("androidx.material.icons.extended").get().let { add("implementation", it) }
        libs.findLibrary("androidx.navigation.compose").get().let { add("implementation", it) }
        libs.findLibrary("androidx.ui.tooling.preview").get().let { add("implementation", it) }
        libs.findLibrary("material").get().let { add("implementation", it) }
        libs.findLibrary("androidx.material3").get().let { add("implementation", it) }

        if (target == Target.UTILS) return@dependencies

        add("implementation", project(":utils:compose"))
    }
}
