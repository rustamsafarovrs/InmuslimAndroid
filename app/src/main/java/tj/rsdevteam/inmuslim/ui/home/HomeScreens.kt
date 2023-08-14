package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import tj.rsdevteam.inmuslim.ui.common.ErrorDialog
import tj.rsdevteam.inmuslim.ui.common.ProgressBar
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTypography

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigateToSelectRegion: () -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    ErrorDialog(viewModel.dialogState.value)
    if (viewModel.openSelectRegion.value == true) {
        navigateToSelectRegion.invoke()
        viewModel.openSelectRegion.value = null
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showLoading.value) {
            ProgressBar()
        }
        if (viewModel.timingText.value.isNotEmpty()) {
            Text(
                text = viewModel.timingText.value,
                style = InmuslimTypography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}