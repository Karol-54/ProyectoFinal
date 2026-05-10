package com.jaimes.nodocivico.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaimes.nodocivico.R
import com.jaimes.nodocivico.model.Report

class ReportAdapter(
    private val onReportClick: (Report) -> Unit
) : ListAdapter<Report, ReportAdapter.ReportViewHolder>(ReportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvItemCategory)
        private val tvPriority: TextView = itemView.findViewById(R.id.tvItemPriority)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvItemStatus)

        fun bind(report: Report) {
            tvTitle.text = report.title
            tvCategory.text = "Categoría: ${report.category}"
            tvPriority.text = "Prioridad: ${report.priority}"
            tvStatus.text = report.status
            when (report.status) {
                "Pendiente" -> tvStatus.setBackgroundColor(0xFFF57C00.toInt())
                "En proceso" -> tvStatus.setBackgroundColor(0xFF1565C0.toInt())
                "Cerrado" -> tvStatus.setBackgroundColor(0xFF2E7D32.toInt())
            }
            itemView.setOnClickListener { onReportClick(report) }
        }
    }

    class ReportDiffCallback : DiffUtil.ItemCallback<Report>() {
        override fun areItemsTheSame(oldItem: Report, newItem: Report) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Report, newItem: Report) = oldItem == newItem
    }
}