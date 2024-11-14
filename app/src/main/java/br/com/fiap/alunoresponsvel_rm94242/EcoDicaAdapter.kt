package br.com.fiap.alunoresponsvel_rm94242

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.alunoresponsvel_rm94532.R

class EcoDicaAdapter(private var dicas: List<EcoDica>) : RecyclerView.Adapter<EcoDicaAdapter.EcoDicaViewHolder>(), Filterable {

    private var dicasFiltradas: List<EcoDica> = dicas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoDicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ecodica, parent, false)
        return EcoDicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EcoDicaViewHolder, position: Int) {
        val ecoDica = dicasFiltradas[position]
        holder.bind(ecoDica)
    }

    override fun getItemCount(): Int {
        return dicasFiltradas.size
    }

    inner class EcoDicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.tituloDica)
        private val descricaoTextView: TextView = itemView.findViewById(R.id.descricaoDica)

        fun bind(dica: EcoDica) {
            tituloTextView.text = dica.titulo
            descricaoTextView.text = dica.descricao

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Dica: ${dica.titulo}\n${dica.descricao}", Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://brasilescola.uol.com.br/geografia/desenvolvimento-sustentavel.htm"))
                itemView.context.startActivity(browserIntent)
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtroResult = FilterResults()
                dicasFiltradas = if (constraint.isNullOrEmpty()) {
                    dicas
                } else {
                    dicas.filter {
                        it.titulo.contains(constraint, ignoreCase = true) ||
                                it.descricao.contains(constraint, ignoreCase = true)
                    }
                }
                filtroResult.values = dicasFiltradas
                return filtroResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dicasFiltradas = results?.values as List<EcoDica>
                notifyDataSetChanged()
            }
        }
    }
}
