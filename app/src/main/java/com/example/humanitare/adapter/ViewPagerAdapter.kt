package com.example.humanitare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.R
import com.example.humanitare.model.Organization

class ViewPagerAdapter(private val mockDataList: List<Organization>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ImgOrganizationIcon = itemView.findViewById<ImageView>(R.id.ImgOrganizationIcon)
        private val lblOrganizationTitle = itemView.findViewById<TextView>(R.id.lblOrganizationTitle)
        private val lblOrganizationDescription = itemView.findViewById<TextView>(R.id.lblOrganizationDescription)

        fun bind(mockData: Organization) {
            ImgOrganizationIcon.setImageResource(mockData.imageResId)
            lblOrganizationTitle.text = mockData.title
            lblOrganizationDescription.text = mockData.description
        }
    }

    //Temporary mock data for debugging purposes
    companion object {
        private val mockDataList = listOf(
            Organization("Fajter", "Vizija fajtera je unaprijediti društveni položaj i institucionalni okvir u kojem se nalaze beskućnici putem provedbe konkretnih društvenih akcija i projekata.", R.drawable.logo_fajter_min),
            Organization("Noina Arka", " Osnovna djelatnost Udruge je pomoć napuštenim životinjama, te pomoć i potpora ljudima koji brinu o većem broju životinja.", R.drawable.logo_noina_arka_min),
            Organization("Savao", "Udruga Savao nastoji pomoći ljudima u potrebi i na rubu društva. Osnovali su je volonteri iz pučke kuhinje Misionarki ljubavi u Zagrebu.", R.drawable.logo_savao_min),
            Organization("SOS Djecje Selo", "Konceptom SOS Dječjeg sela naša je organizacija utrla put obiteljskom pristupu dugoročne skrbi napuštene djece i djece bez roditelja i odgovarajuće roditeljske skrbi.", R.drawable.logo_sos_djecje_selo_min),
            Organization("Srce Split", "Naš cilj je unapređenje kvalitete života osoba s invaliditetom i njihovih obitelji.", R.drawable.logo_udruga_srce_split_min)
        )

        fun create() = ViewPagerAdapter(mockDataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mockDataList[position])
    }

    override fun getItemCount() = mockDataList.size

}