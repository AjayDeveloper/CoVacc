package com.example.covacc.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covacc.R
import com.example.covacc.model.Model

class CenterAdapter(private val centerList: List<Model>) :
    RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {

    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val centerName: TextView = itemView.findViewById(R.id.txtCenterName)
        val centerAddress: TextView = itemView.findViewById(R.id.txtCenterLocation)
        val centerTiming: TextView = itemView.findViewById(R.id.txtCenterTiming)
        val vaccineName: TextView = itemView.findViewById(R.id.txtVaccineName)
        val vaccineType: TextView = itemView.findViewById(R.id.txtVaccineType)
        val ageLimit: TextView = itemView.findViewById(R.id.txtAgeLimit)
        val vaccineAva: TextView = itemView.findViewById(R.id.txtVaccineAva)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)

        return CenterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val center = centerList[position]
        holder.centerName.text = center.centerName
        holder.centerAddress.text = center.centerAddress
        holder.centerTiming.text = ("From : " + center.centerFromTime + "To " + center.centerToTime)
        holder.vaccineName.text = center.vaccinName
        holder.vaccineType.text = center.fees_Type
        holder.vaccineAva.text = ("Availablity:" + center.availableCapacity.toString())
        holder.ageLimit.text = ("Age Limit : "+ center.ageLimit.toString())

        return

    }

    override fun getItemCount(): Int {

        return centerList.size
    }
}