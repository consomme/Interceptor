package net.cnsmm.interceptor

import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import net.cnsmm.interceptor.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val pm: PackageManager = packageManager
        val list = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .map { info -> AppInfoModel(info.loadIcon(pm), info.loadLabel(pm), info.packageName) }
                .toMutableList()

        val adapter = ApplicationAdapter(list) { appInfoModel ->
            createShortcut(appInfoModel)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun createShortcut(appInfoModel: AppInfoModel) {
        val shortcutIntent = InterceptActivity.createIntent(this, appInfoModel.packageName);
        shortcutIntent.action = Intent.ACTION_MAIN
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val intent = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appInfoModel.name);
        sendBroadcast(intent);

        finish()
    }
}
