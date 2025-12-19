package org.plantalpha.maimaidata.feature.songdetails.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailsTopBar(
    title: String = "Title",
    artist: String = "artist",
    color: Color = Color.Magenta,
    onBack: () -> Unit = { },
    onFavorite: () -> Unit = { },
) {
    val textColor = MaterialTheme.colorScheme.background
    TopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artist,
                    fontSize = 16.sp,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = textColor
                )
            }
        },
        actions = {
            IconButton(onFavorite) {
                Icon(
                    imageVector = Icons.Sharp.Favorite,
                    contentDescription = "Favorite",
                    tint = textColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
        )
    )
}