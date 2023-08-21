package tj.rsdevteam.inmuslim.ui.settings

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.R
import tj.rsdevteam.inmuslim.ui.launch.LaunchActivity
import tj.rsdevteam.inmuslim.ui.region.RegionScreen
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTypography
import tj.rsdevteam.inmuslim.utils.findActivity

/**
 * Created by Rustam Safarov on 8/19/23.
 * github.com/rustamsafarovrs
 */

@Composable
fun SettingItem(onClick: () -> Unit, icon: ImageVector, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = "title")
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = InmuslimTypography.titleLarge)
            Text(text = desc, style = InmuslimTypography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionsBottomSheet(onDismiss: () -> Unit) {
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
        sheetState = state,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        RegionScreen {
            scope.launch {
                state.hide()
                onDismiss.invoke()
                Intent(context, LaunchActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(this)
                }
                context.findActivity().finish()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(popBackStack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var regionsBottomSheetState by remember { mutableStateOf(false) }

    if (regionsBottomSheetState) {
        RegionsBottomSheet {
            regionsBottomSheetState = false
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { popBackStack.invoke() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
        ) {
            SettingItem(
                icon = Icons.Outlined.LocationOn,
                title = "Change Region",
                desc = "Change to another Region",
                onClick = { regionsBottomSheetState = true }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
