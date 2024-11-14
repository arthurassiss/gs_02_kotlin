package br.com.fiap.alunoresponsvel_rm94242

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import br.com.fiap.alunoresponsvel_rm94532.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var ecoDicaAdapter: EcoDicaAdapter
    private lateinit var dbHelper: EcoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = EcoDatabaseHelper(this)

        if (dbHelper.getAllDicas().isEmpty()) {
            insertInitialDicas()
        }

        val dicas = dbHelper.getAllDicas()
        ecoDicaAdapter = EcoDicaAdapter(dicas)
        recyclerView.adapter = ecoDicaAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                ecoDicaAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ecoDicaAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun insertInitialDicas() {
        val dicasIniciais = listOf(
            EcoDica("Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar."),
            EcoDica("Use lâmpadas LED", "Lâmpadas LED consomem até 80% menos energia do que as tradicionais."),
            EcoDica("Evite o uso excessivo de água", "A água é um recurso natural limitado. Tome banhos mais curtos e evite desperdício."),
            EcoDica("Compre produtos locais", "Produtos locais não demandam transporte a longas distâncias, reduzindo a emissão de gases poluentes.")
        )
        dicasIniciais.forEach { dbHelper.insertDica(it) }
    }
}
