package tj.rsdevteam.inmuslim.data.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

class Preferences(context: Context) {

    private val prefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    companion object {

        const val PREFS_REGION_ID = "region_id"
        const val PREFS_USER_ID = "user_id"
        const val PREFS_FIREBASE_TOKEN = "firebase_token"
    }

    fun saveRegionId(id: Long) {
        editor.putLong(PREFS_REGION_ID, id).apply()
    }

    fun getRegionId(): Long = prefs.getLong(PREFS_REGION_ID, -1)

    fun saveUserId(id: Long) {
        editor.putLong(PREFS_USER_ID, id).apply()
    }

    fun getUserId() = prefs.getLong(PREFS_USER_ID, -1)


    fun saveFirebaseToken(token: String) {
        editor.putString(PREFS_FIREBASE_TOKEN, token).apply()
    }

    fun getFirebaseToken(): String {
        return prefs.getString(PREFS_FIREBASE_TOKEN, null) ?: ""
    }
}
