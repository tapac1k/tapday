#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#set($SCREEN_NAME_ = $SCREEN_NAME)
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme

@Composable
fun ${SCREEN_NAME}Screen(
    viewModel: ${SCREEN_NAME}ViewModel = viewModel(),

    ) {
    ${SCREEN_NAME}ScreenContent()
}

@Composable
fun ${SCREEN_NAME}ScreenContent(
) {
     Column {
       
        }
}

@Composable
fun ${SCREEN_NAME}ScreenPreview() {
    TapMyDayTheme {
        ${SCREEN_NAME}ScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ${SCREEN_NAME}ScreenPreviewLight() {
    ${SCREEN_NAME}ScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ${SCREEN_NAME}ScreenPreviewDark() {
    ${SCREEN_NAME}ScreenPreview()
}
