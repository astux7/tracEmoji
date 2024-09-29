package com.basta.guessemoji.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants.BASE_URL_POLICY
import com.basta.guessemoji.common.utils.onLinkClick
import com.basta.guessemoji.components.CustomButton
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = getViewModel(),
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        CustomButton(Modifier, R.string.privacy_policy_label) {
            context.onLinkClick(BASE_URL_POLICY)
        }

        Text(text = stringResource(id = R.string.app_version_label, viewModel.appVersion))
    }
}