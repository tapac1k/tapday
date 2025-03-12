package com.tapac1k.compose.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme

@Composable
inline fun TopBar(
    vararg icons: Pair<ImageVector, () -> Unit>,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .shadow(4.dp, shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ).windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
        Spacer(Modifier.height(50.dp).weight(1f))
        for (icon in icons) {
            IconButton(onClick = icon.second) {
                Icon(icon.first, "")
            }
        }
    }
}

@Preview(heightDp = 320)
@Composable
fun TopBarPreview() {
    TapMyDayTheme {
        Surface(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
            TopBar(
                icons = arrayOf(
                    Icons.Filled.Settings to {},
                    Icons.Filled.AddCard to {}
                )
            ) {
                Text("Hello")
                Text("Bye")
            }
        }

    }
}

@Preview(heightDp = 320, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarPreviewDark() {
    TapMyDayTheme {
        Surface(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
            TopBar(
                icons = arrayOf(
                    Icons.Filled.Settings to {},
                    Icons.Filled.AddCard to {}
                )
            ) {
                Text("Hello")
                Text("Bye")
            }
        }

    }
}