package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tj.rsdevteam.inmuslim.R
import tj.rsdevteam.inmuslim.data.models.Timing
import tj.rsdevteam.inmuslim.ui.common.ErrorDialog
import tj.rsdevteam.inmuslim.ui.common.ProgressBar
import tj.rsdevteam.inmuslim.ui.theme.InmuslimShapes
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTheme
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTypography

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Composable
fun TimeItem(title: String, start: String) {
    Row(
        modifier = Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navigateToSettings.invoke() }) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                })
        }
    ) { paddingValues ->
        ErrorDialog(viewModel.dialogState.value)
        if (viewModel.showLoading.value) {
            ProgressBar()
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            if (viewModel.timing.value != null) {
                val timing = viewModel.timing.value!!
                TimeItems(timing)
            }
        }
    }
}

@Composable
private fun TimeItems(timing: Timing) {
    Column {
        Spacer(modifier = Modifier.weight(0.6f))
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
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun TimeItemPreview() {
    InmuslimTheme {
        TimeItem(title = "title", start = "00:00:00")
    }
}