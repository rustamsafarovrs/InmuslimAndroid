package tj.rsdevteam.inmuslim.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

class Preferences(context: Context, private val moshi: Moshi) {

    private val prefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    companion object {

        const val PREFS_REGION_ID = "region_id"
    }

    fun saveRegionId(id: Long) {
        editor.putLong(PREFS_REGION_ID, id).apply()
    }

    fun getRegionId(): Long = prefs.getLong(PREFS_REGION_ID, -1)
}