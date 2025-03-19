package com.basta.guessemoji.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


// Menu
@Composable fun TopMenuItemStyle() = TextStyle(fontWeight = FontWeight.W700)
@Composable fun TopMenuIconStyle() = TextStyle(fontWeight = FontWeight.W700, fontSize = 24.sp)

@Composable fun ProfileIconStyle() = TextStyle(fontSize = 45.sp, textAlign = TextAlign.Center)
@Composable fun ProfileLevelStyle() = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 16.sp)

// HOME
@Composable fun HomeBoxItemStyle() = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.W700, textAlign = TextAlign.Center)
@Composable fun HomeAlertBoxTitleStyle() = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.W700, fontSize = 18.sp)
@Composable fun HomeAlertBoxCTAStyle() = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.W700, fontSize = 16.sp)
@Composable fun HomeAlertBoxTextStyle() = TextStyle(fontWeight = FontWeight.Normal, textAlign = TextAlign.Start, fontSize = 16.sp, lineHeight = 28.sp)

