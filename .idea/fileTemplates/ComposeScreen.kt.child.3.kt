#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
import androidx.lifecycle.SavedStateHandle
import com.tapac1k.compose.ViewModelWithUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ${SCREEN_NAME}ViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
) : ViewModelWithUpdater<${SCREEN_NAME}State, ${SCREEN_NAME}Event, ${SCREEN_NAME}Updater>(
    ${SCREEN_NAME}State()
) {

    override fun processUpdate(updater: ${SCREEN_NAME}Updater) {
        TODO("Not yet implemented")
    }
}