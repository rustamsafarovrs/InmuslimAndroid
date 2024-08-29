package tj.rsdevteam.inmuslim.utils

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * Created by Rustam Safarov on 6/25/24.
 * github.com/rustamsafarovrs
 */

fun Activity.launchInAppReview(
    onComplete: (() -> Unit)? = null,
) {
    val reviewManager = ReviewManagerFactory.create(this)
    reviewManager.requestReviewFlow().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            reviewManager.launchReviewFlow(this, task.result).addOnCompleteListener { onComplete?.invoke() }
        }
    }
}
