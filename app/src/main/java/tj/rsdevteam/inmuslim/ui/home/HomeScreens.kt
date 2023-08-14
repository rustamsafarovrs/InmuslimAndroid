package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tj.rsdevteam.inmuslim.ui.common.ErrorDialog
import tj.rsdevteam.inmuslim.ui.common.ProgressBar
import tj.rsdevteam.inmuslim.ui.theme.InmuslimShapes
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTypography
import tj.rsdevteam.inmuslim.ui.theme.primaryColor

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Composable
fun TimeItem(title: String, start: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .defaultMinSize(minHeight = 80.dp)
            .clip(shape = InmuslimShapes.large)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = InmuslimTypography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = start, style = InmuslimTypography.titleSmall)
    }
}

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
        if (viewModel.timing.value != null) {
            val timing = viewModel.timing.value!!
            TimeItem(title = "Бомдод", start = timing.fajr)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Тулӯи офтоб", start = timing.sunrise)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Пешин", start = timing.zuhr)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Аср", start = timing.asr)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Гуруби офтоб", start = timing.sunset)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Шом", start = timing.maghrib)
            Spacer(modifier = Modifier.height(12.dp))
            TimeItem(title = "Хуфтан", start = timing.isha)
        }
    }
}