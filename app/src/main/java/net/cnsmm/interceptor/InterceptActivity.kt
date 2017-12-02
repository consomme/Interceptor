package net.cnsmm.interceptor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity

class InterceptActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_PARAM_PACKAGE_NAME = "package_name"

        fun createIntent(context: Context, targetPackage: String): Intent {
            val intent = Intent(context, InterceptActivity::class.java)
            intent.putExtra(INTENT_PARAM_PACKAGE_NAME, targetPackage)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Disable USB Debugging
        Settings.Global.putString(contentResolver, Settings.Global.ADB_ENABLED, "0")

        val targetPackage = intent.getStringExtra(INTENT_PARAM_PACKAGE_NAME)
        startActivity(packageManager.getLaunchIntentForPackage(targetPackage))

        finish()
    }
}