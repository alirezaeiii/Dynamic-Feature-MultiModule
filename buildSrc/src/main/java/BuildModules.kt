import kotlin.reflect.full.memberProperties

/**
 * Configuration of build modules
 */
object BuildModules {
    const val APP = ":app"
    const val CORE = ":core"
    const val COMMON = ":common"
    const val FEATURE_LIST = ":feature_list"

    /*
     Return list of feature modules in the project
     */
    fun getFeatureModules(): Set<String> {
        val featurePrefix = ":feature_"

        return getAllModules()
            .filter { it.startsWith(featurePrefix) }
            .toSet()
    }

    /*
    Return list of all modules in the project
    */
    private fun getAllModules() = BuildModules::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}