package com.example.s205446_lykkehjulet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s205446_lykkehjulet.MainActivity
import com.example.s205446_lykkehjulet.R

/**
 *  Struktur og kode fra Words/Navigation Codelab er brugt som skelet for fragmentet.
 *  ViewHolder-metoderne er fra 'WordAdapter'-klassen fra Words-Codelabbet.
 */

class RecyclerViewAdapter(private val letters: List<String>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.WordViewHolder>() {

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val bogstaver = view.findViewById<TextView>(R.id.button_mini_item)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerwords_view, parent, false)
        return WordViewHolder(layout)
    }

    /**
     * Viser bogstaverne fra ordet i recyclerViewet, når de er blevet gættet og indgår i listen af
     * gættede bogstaver.
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = letters[position]
        // Needed to call startActivity
        val context = holder.view.context


        if (item in (context as MainActivity).liste) {
            holder.bogstaver.text = item.uppercase()
        }
    }
        // ItemCount gør jeg ikke brug af i nogen som helst sammenhænge
    override fun getItemCount(): Int = letters.size
}