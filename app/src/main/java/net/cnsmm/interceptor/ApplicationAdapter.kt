package net.cnsmm.interceptor

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.cnsmm.interceptor.databinding.ListItemApplicationBinding

class ApplicationAdapter(private val appInfoList: MutableList<AppInfoModel>,
                         private val onItemClickListener: (AppInfoModel) -> Unit) : RecyclerView.Adapter<ApplicationAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return appInfoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = appInfoList[position]

        holder.binding.appIconImageView.setImageDrawable(info.icon)
        holder.binding.appNameTextView.text = info.name

        holder.itemView.setOnClickListener({ onItemClickListener(info) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.list_item_application, parent, false))
    }

    class ViewHolder(val binding: ListItemApplicationBinding) : RecyclerView.ViewHolder(binding.root)
}
