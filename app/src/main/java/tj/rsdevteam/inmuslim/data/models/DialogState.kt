package tj.rsdevteam.inmuslim.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

data class DialogState(
    val msg: String?,
    var isDismissed: MutableState<Boolean> = mutableStateOf(false)
) {

    fun dismissDialog() {
        isDismissed.value = true
    }
}
