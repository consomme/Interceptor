package net.cnsmm.interceptor

import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import net.cnsmm.interceptor.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val pm: PackageManager = packageManager
        val list = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .map { appInfo -> AppInfoModel(appInfo.loadIcon(pm), appInfo.loadLabel(pm).toString(), appInfo.packageName) }
                .sortedWith(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
                .toMutableList()

        val adapter = ApplicationAdapter(list) { appInfoModel ->
            createShortcut(appInfoModel)

            Toast.makeText(this,
                    getString(R.string.create_shortcut_succeeded, appInfoModel.name),
                    Toast.LENGTH_SHORT).show()

            finish()
        }
        binding.recyclerView.adapter = adapter
    }

    private fun createShortcut(appInfoModel: AppInfoModel) {
        val intent = InterceptActivity.createIntent(this, appInfoModel.packageName);
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val iconBitmapDrawable = appInfoModel.icon as BitmapDrawable
        val shortcutInfoCompat = ShortcutInfoCompat.Builder(this, "interceptor")
                .setShortLabel(appInfoModel.name)
                .setLongLabel(appInfoModel.name)
                .setIcon(IconCompat.createWithBitmap(iconBitmapDrawable.bitmap))
                .setIntent(intent)
                .build()
        ShortcutManagerCompat.requestPinShortcut(this, shortcutInfoCompat, null)
    }
}
