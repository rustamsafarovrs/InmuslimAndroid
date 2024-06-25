package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.data.models.DialogState
import tj.rsdevteam.inmuslim.data.models.Timing
import tj.rsdevteam.inmuslim.data.models.network.RegisterUserBody
import tj.rsdevteam.inmuslim.data.models.network.Resource
import tj.rsdevteam.inmuslim.data.repositories.TimingRepository
import tj.rsdevteam.inmuslim.data.repositories.UserRepository
import tj.rsdevteam.inmuslim.utils.Utils
import javax.inject.Inject

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val timingRepository: TimingRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // region properties
    var dialogState = mutableStateOf<DialogState?>(null)
        private set
    var showLoading = mutableStateOf(false)
        private set
    var timing = mutableStateOf<Timing?>(null)
        private set
    // endregion

    init {
        if (userRepository.getUserId() != -1L) {
            if (userRepository.getFirebaseToken().isEmpty()) {
                updateMessagingId()
            }
        }
        refresh()
    }

    // region network
    private fun getTiming() {
        viewModelScope.launch {
            timingRepository.getTiming()
                .collect { rs ->
                    when (rs) {
                        is Resource.InProgress -> Unit
                        is Resource.Success -> timing.value = rs.data.timing
                        is Resource.Error -> dialogState.value = DialogState(rs.error?.message)
                    }
                    showLoading.value = rs is Resource.InProgress
                }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            userRepository.registerUser(RegisterUserBody(Utils.getDeviceInfo()))
                .collect { rs ->
                    when (rs) {
                        is Resource.InProgress -> Unit
                        is Resource.Success -> updateMessagingId()
                        is Resource.Error -> Unit
                    }
                }
        }
    }

    private fun updateMessagingId() {
        viewModelScope.launch {
            userRepository.updateMessagingId()
                .collect { rs ->
                    when (rs) {
                        is Resource.InProgress -> Unit
                        is Resource.Success -> Unit
                        is Resource.Error -> Unit
                    }
                }
        }
    }
    // endregion

    private fun refresh() {
        getTiming()
        if (userRepository.needRegister()) {
            registerUser()
        }
    }
}
