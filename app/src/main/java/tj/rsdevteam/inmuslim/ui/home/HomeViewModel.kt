package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.data.models.DialogState
import tj.rsdevteam.inmuslim.data.models.network.GetTimingBody
import tj.rsdevteam.inmuslim.data.models.network.Status
import tj.rsdevteam.inmuslim.data.repositories.RegionRepository
import tj.rsdevteam.inmuslim.data.repositories.TimingRepository
import javax.inject.Inject

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val regionRepository: RegionRepository,
    private val timingRepository: TimingRepository
) : ViewModel() {

    // region properties
    var dialogState = mutableStateOf<DialogState?>(null)
        private set
    var showLoading = mutableStateOf(false)
        private set
    var timingText = mutableStateOf("")
        private set
    var openSelectRegion = mutableStateOf<Boolean?>(null)
        private set
    // endregion

    // region network
    private fun getTiming() {
        viewModelScope.launch {
            timingRepository.getTiming()
                .collect { rs ->
                    when (rs.status) {
                        Status.LOADING -> Unit
                        Status.SUCCESS -> timingText.value = rs.data.toString()
                        Status.ERROR -> dialogState.value = DialogState(rs.message)
                    }
                    showLoading.value = rs.status == Status.LOADING
                }
        }
    }
    // endregion

    fun refresh(){
        if (regionRepository.getRegionId() > 0) {
            getTiming()
        } else {
            openSelectRegion.value = true
        }
    }
}