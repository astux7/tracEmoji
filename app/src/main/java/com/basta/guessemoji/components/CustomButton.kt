package com.basta.guessemoji.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.R
import com.basta.guessemoji.ui.theme.borderColor

@Composable
fun CustomButton(
    modifier: Modifier,
    label: Int,
    showIcon: Boolean = true,
    params: String? = null,
    onEvent: () -> Unit
) {
    val text = if (params.isNullOrBlank()) {
        stringResource(id = label)
    } else {
        stringResource(id = label) + params
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 10.dp)
            .clickable { onEvent() },
        colors = CardDefaults.cardColors(
            containerColor = borderColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(15),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = if (showIcon) TextAlign.Left else TextAlign.Center
            )

            if (showIcon) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = stringResource(id = R.string.arrow_label)
                )
            }

        }
    }
}