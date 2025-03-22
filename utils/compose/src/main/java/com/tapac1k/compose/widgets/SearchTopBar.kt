package com.tapac1k.compose.widgets

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tapac1k.compose.theme.TapMyDayTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    query: String? = null,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onClear: () -> Unit = {},
    onBack: (() -> Unit)? = {},
    content: @Composable RowScope.() -> Unit = {},
) {
    Column(modifier) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(12f)
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
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Filled.Search, "Search")
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .offset(y = -16.dp)
                .zIndex(1f),
            visible = query != null
        ) {

            SearchBarDefaults.InputField(
                query.orEmpty(),
                onQueryChange = { onQueryChange(it) },
                onSearch = { onQueryChange(it) },
                expanded = true,
                placeholder = { Text("Search", Modifier.alpha(.5f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(top = 16.dp),
                onExpandedChange = {},
                trailingIcon = {
                    IconButton(onClick = { onClear() }) {
                        Icon(Icons.Filled.Close, "")
                    }
                }
            )
        }
    }
}

@Composable
fun SearchTopBarPreview(query: String?) {
    TapMyDayTheme {
        Surface {
            Column {
                TopBarWithSearch(query)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SearchTopBarPreviewLight() {
    SearchTopBarPreview("asd")
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchTopBarPreviewDark() {
    SearchTopBarPreview("")
}