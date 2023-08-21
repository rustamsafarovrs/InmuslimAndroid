package tj.rsdevteam.inmuslim.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.data.models.DialogState
import tj.rsdevteam.inmuslim.data.models.Timing
import tj.rsdevteam.inmuslim.data.models.network.RegisterUserBody
import tj.rsdevteam.inmuslim.data.models.network.Status
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
                    when (rs.status) {
                        Status.LOADING -> Unit
                        Status.SUCCESS -> timing.value = rs.data!!.timing
                        Status.ERROR -> dialogState.value = DialogState(rs.message)
                    }
                    showLoading.value = rs.status == Status.LOADING
                }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            userRepository.registerUser(RegisterUserBody(Utils.getDeviceInfo()))
                .collect { rs ->
                    when (rs.status) {
                        Status.LOADING -> Unit
                        Status.SUCCESS -> updateMessagingId()
                        Status.ERROR -> Unit
                    }
                }
        }
    }

    private fun updateMessagingId() {
        viewModelScope.launch {
            userRepository.updateMessagingId()
                .collect { rs ->
                    when (rs.status) {
                        Status.LOADING -> Unit
                        Status.SUCCESS -> Unit
                        Status.ERROR -> Unit
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
