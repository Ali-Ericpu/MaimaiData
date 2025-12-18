package org.plantalpha.maimaidata.feature.song.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.ui.component.BasicDialog

@Composable
fun UpdateDataVersionDialog(
    content: String = "",
    onCancel: () -> Unit = { },
    onConfirm: () -> Unit = { }
) {
    BasicDialog(
        title = stringResource(R.string.new_version_detected),
        confirmLabel = stringResource(R.string.update),
        onCancel = onCancel,
        onConfirm = onConfirm
    ) {
        Text(
            text = content,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}