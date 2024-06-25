package tj.rsdevteam.inmuslim.ui.region

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.data.models.DialogState
import tj.rsdevteam.inmuslim.data.models.Region
import tj.rsdevteam.inmuslim.data.models.Resource
import tj.rsdevteam.inmuslim.data.repositories.RegionRepository
import javax.inject.Inject

/**
 * Created by Rustam Safarov on 14/08/23.
 * github.com/rustamsafarovrs
 */

@HiltViewModel
class RegionViewModel
@Inject constructor(
    private val regionRepository: RegionRepository
) : ViewModel() {

    // region properties
    var list = mutableStateOf<List<Region>>(emptyList())
        private set
    var showLoading = mutableStateOf(false)
        private set
    var dialogState = mutableStateOf<DialogState?>(null)
        private set
    // endregion

    // region network
    init {
        getRegions()
    }

    private fun getRegions() {
        viewModelScope.launch {
            regionRepository.getRegions().collect { rs ->
                when (rs) {
                    is Resource.InProgress -> Unit
                    is Resource.Success -> list.value = rs.data
                    is Resource.Error -> dialogState.value = DialogState(rs.error?.message)
                }
                showLoading.value = rs is Resource.InProgress
            }
        }
    }
    // endregion

    fun onConfirmBtnClick() {
        val r = list.value.firstOrNull { it.selected.value }
        if (r != null) {
            regionRepository.saveRegionId(r.id)
        }
    }

    fun onRegionSelected(region: Region) {
        list.value.forEach {
            if (it != region) {
                it.selected.value = false
            }
        }
        region.selected.value = true
    }
}
