package tj.rsdevteam.inmuslim.utils

import android.os.Build

/**
 * Created by Rustam Safarov on 15/08/23.
 * github.com/rustamsafarovrs
 */

object Utils {

    fun getDeviceInfo(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}, Android ${Build.VERSION.RELEASE}, API ${Build.VERSION.SDK_INT}"
    }
}
