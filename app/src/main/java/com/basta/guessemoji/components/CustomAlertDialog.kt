package com.basta.guessemoji.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.basta.guessemoji.R
import com.basta.guessemoji.common.EmojiConstants.CART_EMOJI
import com.basta.guessemoji.common.HomeAlertBoxCTAStyle
import com.basta.guessemoji.common.HomeAlertBoxTextStyle
import com.basta.guessemoji.common.HomeAlertBoxTitleStyle
import com.basta.guessemoji.ui.theme.ButtonBorder
import com.basta.guessemoji.ui.theme.borderColor

@Composable
fun CustomAlertDialog(
    title: String,
    text: String,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onOkRequest: () -> Unit,
    onBuyRequest: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = Modifier.heightIn(min = 224.dp).clip(RoundedCornerShape(15.dp)).border(1.dp, ButtonBorder, RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        onDismissRequest = { onDismissRequest.invoke() },
        title = {
            Text(
                text = title,
                style = HomeAlertBoxTitleStyle(),
            )
        },
        text = {
            Text(
                text = text,
                style = HomeAlertBoxTextStyle(),
            )
        },
        confirmButton = {
            onBuyRequest?.let {
                TextButton(onClick = { it.invoke() }, border = BorderStroke(1.dp, ButtonBorder), shape = RoundedCornerShape(15), colors = ButtonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onSurface, Color.White, Color.White)) {
                    Text(
                        text = stringResource(id = R.string.buy_label, CART_EMOJI),
                        style = HomeAlertBoxCTAStyle(),
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onOkRequest.invoke() }, border = BorderStroke(1.dp, ButtonBorder), shape = RoundedCornerShape(15), colors = ButtonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onSurface, Color.White, Color.White)) {
                Text(
                    text = buttonText,
                    style = HomeAlertBoxCTAStyle(),
                )
            }
        }
    )
}