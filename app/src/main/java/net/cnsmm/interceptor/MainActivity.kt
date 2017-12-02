package net.cnsmm.interceptor

import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
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
        val list: MutableList<AppInfoModel> = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .map { info -> AppInfoModel(info.loadIcon(pm), info.loadLabel(pm)) }
                .toMutableList()

        val adapter = ApplicationAdapter(list) { appInfoModel ->
            Toast.makeText(this, appInfoModel.name, Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
    }
}
