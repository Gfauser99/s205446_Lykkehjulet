package com.example.s205446_lykkehjulet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s205446_lykkehjulet.MainActivity
import com.example.s205446_lykkehjulet.databinding.LosingScreenFragmentBinding

/**
 * Struktur og kode (Binding, inflator, container) fra Words/Navigation Codelab er brugt som
 * skelet for fragmentet.
 */
class LosingScreenFragment : Fragment() {
    private var _binding: LosingScreenFragmentBinding? = null


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LosingScreenFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * i onViewCreated bliver tekst sat samt en onCLickListener på en knap, som kan starte et nyt spil med
     * resetGame() metoden.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mActivity = (activity as MainActivity)
        binding.gameLost.text
        binding.losePlayAgainButton.setOnClickListener {
            mActivity.resetGame()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}