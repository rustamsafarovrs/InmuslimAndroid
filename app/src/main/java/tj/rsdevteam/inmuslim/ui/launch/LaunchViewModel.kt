package tj.rsdevteam.inmuslim.ui.launch

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tj.rsdevteam.inmuslim.data.repositories.RegionRepository
import javax.inject.Inject

/**
 * Created by Rustam Safarov on 8/20/23.
 * github.com/rustamsafarovrs
 */

@HiltViewModel
class LaunchViewModel
@Inject constructor(
    private val regionRepository: RegionRepository
) : ViewModel() {

    private val _openMain = MutableStateFlow(false)
    val openMain = _openMain.asStateFlow()

    private val _openOnboarding = MutableStateFlow(false)
    val openOnboarding = _openOnboarding.asStateFlow()

    init {
        if (regionRepository.getRegionId() > 0) {
            _openMain.value = true
        } else {
            _openOnboarding.value = true
        }
    }
}