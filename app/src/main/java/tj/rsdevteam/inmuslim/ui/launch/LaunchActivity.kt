package tj.rsdevteam.inmuslim.ui.launch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tj.rsdevteam.inmuslim.ui.MainActivity
import tj.rsdevteam.inmuslim.ui.OnboardingActivity

/**
 * Created by Rustam Safarov on 8/20/23.
 * github.com/rustamsafarovrs
 */

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {

    private val viewModel by viewModels<LaunchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.openMain.collect {
                        if (it) {
                            openMain()
                            finish()
                        }
                    }
                }

                launch {
                    viewModel.openOnboarding.collect {
                        if (it) {
                            openOnboarding()
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun openMain() {
        Intent(this@LaunchActivity, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun openOnboarding() {
        Intent(this@LaunchActivity, OnboardingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }
}