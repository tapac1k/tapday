#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

data class ${SCREEN_NAME}State(
    val loading: Boolean = true,
)
