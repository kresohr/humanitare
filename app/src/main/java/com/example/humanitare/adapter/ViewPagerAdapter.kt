package com.example.humanitare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.R
import com.example.humanitare.model.Organization

class ViewPagerAdapter(private val mockDataList: List<Organization>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(organization: Organization)
    }
    inner class ViewHolder(itemView: View, private val itemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val ImgOrganizationIcon = itemView.findViewById<ImageView>(R.id.ImgOrganizationIcon)
        private val lblOrganizationTitle = itemView.findViewById<TextView>(R.id.lblOrganizationTitle)
        private val lblOrganizationDescription = itemView.findViewById<TextView>(R.id.lblOrganizationDescription)

        fun bind(mockData: Organization) {
            ImgOrganizationIcon.setImageResource(mockData.imageResId)
            lblOrganizationTitle.text = mockData.title
            lblOrganizationDescription.text = mockData.description

            itemView.setOnClickListener {
                itemClickListener.onItemClick(mockData)
            }
        }
    }

    companion object {
        private val mockDataList = listOf(
            Organization("Fajter", "Vizija fajtera je unaprijediti društveni položaj i institucionalni okvir u kojem se nalaze beskućnici putem provedbe konkretnih društvenih akcija i projekata.", R.drawable.logo_fajter_min,  "0x412a732c8688666de52092dab8fd7b2a5a03940d", "https://web.facebook.com/udrugafajter/?locale=hr_HR&_rdc=1&_rdr", "https://udrugafajter.com/" ),
            Organization("Noina Arka", " Osnovna djelatnost Udruge je pomoć napuštenim životinjama, te pomoć i potpora ljudima koji brinu o većem broju životinja.", R.drawable.logo_noina_arka_min, "0x0e57bdf109277cb6101932c8fa81ebe8e03548c1", "https://web.facebook.com/noinaarka?_rdc=1&_rdr", "https://www.noina-arka.hr/"),
            Organization("Savao", "Udruga Savao nastoji pomoći ljudima u potrebi i na rubu društva. Osnovali su je volonteri iz pučke kuhinje Misionarki ljubavi u Zagrebu.", R.drawable.logo_savao_min, "0xbca47ae338c994eed941c78e74f4c431515d3875", "https://web.facebook.com/HUSavao/?_rdc=1&_rdr", "https://udrugasavao.hr/https://udrugasavao.hr/"),
            Organization("SOS Djecje Selo", "Konceptom SOS Dječjeg sela naša je organizacija utrla put obiteljskom pristupu dugoročne skrbi napuštene djece i djece bez roditelja i odgovarajuće roditeljske skrbi.", R.drawable.logo_sos_djecje_selo_min, "0xcd77c325f71291f9a0a482fe58c1a3c5088d3d5a", "https://web.facebook.com/sosdsh?_rdc=1&_rdr", "https://sos-dsh.hr/"),
            Organization("Srce Split", "Naš cilj je unapređenje kvalitete života osoba s invaliditetom i njihovih obitelji.", R.drawable.logo_udruga_srce_split_min, "0xa2acd30a8c1be2dc1fc62a547601c6a6e805b2e1", "https://web.facebook.com/udruga.srce/?_rdc=1&_rdr", "https://srce-cp-split.hr/")
        )

        fun create(): List<Organization> = mockDataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mockDataList[position])
    }

    override fun getItemCount() = mockDataList.size

}