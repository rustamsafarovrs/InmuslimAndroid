package tj.rsdevteam.inmuslim.ui.region

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tj.rsdevteam.inmuslim.R
import tj.rsdevteam.inmuslim.data.models.Region
import tj.rsdevteam.inmuslim.ui.common.ErrorDialog
import tj.rsdevteam.inmuslim.ui.common.PrimaryButton
import tj.rsdevteam.inmuslim.ui.common.ProgressBar
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTypography

/**
 * Created by Rustam Safarov on 14/08/23.
 * github.com/rustamsafarovrs
 */

@Composable
fun Regions(list: List<Region>, onClick: (Region) -> Unit) {
    LazyColumn {
        items(list) { region ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onClick.invoke(region) }
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .defaultMinSize(minHeight = 50.dp)
            ) {
                Text(text = region.name, style = InmuslimTypography.labelLarge)
                Spacer(modifier = Modifier.weight(1f))
                if (region.selected.value) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun RegionScreen(viewModel: RegionViewModel = hiltViewModel(), onSelected: () -> Unit) {
    ErrorDialog(dialogState = viewModel.dialogState.value)
    Column {
        if (viewModel.showLoading.value) {
            ProgressBar()
        } else {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(R.string.select_region_title),
                style = InmuslimTypography.titleLarge,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Regions(viewModel.list.value) {
                viewModel.onRegionSelected(it)
            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "OK") {
                viewModel.onConfirmBtnClick()
                onSelected.invoke()
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
