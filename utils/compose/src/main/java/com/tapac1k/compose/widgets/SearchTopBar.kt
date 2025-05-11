package com.tapac1k.compose.widgets

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tapac1k.compose.theme.TapMyDayTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    query: TextFieldValue?,
    showAdditionalFilters: Boolean = false,
    extraFilterContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onQueryChange: (TextFieldValue) -> Unit = {},
    onClear: () -> Unit = {},
    onBack: (() -> Unit)? = {},
    content: @Composable RowScope.() -> Unit = {},
) {
    var heightPx by remember { mutableStateOf(0) }
    Box(modifier.background(Color.Transparent)) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(12f)
                .background(Color.Transparent)
                .onGloballyPositioned { heightPx = it.size.height }
        ) {
            if (onBack != null) {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                }
            }
            Row(Modifier.weight(1f)) {
                content()
            }
            if (query == null) {
                IconButton(onClick = { onQueryChange(TextFieldValue()) }) {
                    Icon(Icons.Filled.Search, "Search")
                }
            }
        }

            Column(
                Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(top = LocalDensity.current.run { heightPx.toDp() })
                    .zIndex(1f),
            ) {
                if (query != null) {
                    TextField(
                        query,
                        colors = TextFieldDefaults.colors().copy(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        onValueChange = { onQueryChange(it) },
                        placeholder = { Text("Search", Modifier.alpha(.5f)) },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { onClear() }) {
                                Icon(Icons.Filled.Close, "")
                            }
                        }
                    )
                }
                if (showAdditionalFilters) {
                    extraFilterContent?.invoke()
                }

            }



    }
}

@Composable
fun SearchTopBarPreview() {
    TapMyDayTheme {
        Surface {
            Column {
                TopBarWithSearch(null, false)
                TopBarWithSearch(null, true, {
                    Text("Filter 1")
                }, onBack = { /*TODO*/ }, onQueryChange = { /*TODO*/ }, onClear = { /*TODO*/
                })

                TopBarWithSearch(TextFieldValue(), false)

                TopBarWithSearch(TextFieldValue("asd"), true, {
                    Text("Filter 1")
                }, onBack = { /*TODO*/ }, onQueryChange = { /*TODO*/ }, onClear = { /*TODO*/
                })
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SearchTopBarPreviewLight() {
    SearchTopBarPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchTopBarPreviewDark() {
    SearchTopBarPreview()
}