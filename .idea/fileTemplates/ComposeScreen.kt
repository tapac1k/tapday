#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#set($SCREEN_NAME_ = $SCREEN_NAME)

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
   Scaffold() { paddingValues ->
        LazyColumn(contentPadding = paddingValues) { }
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
