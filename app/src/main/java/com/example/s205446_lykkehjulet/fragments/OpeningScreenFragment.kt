package com.example.s205446_lykkehjulet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s205446_lykkehjulet.MainActivity
import com.example.s205446_lykkehjulet.databinding.OpeningFragmentBinding

/**
 * Struktur og kode (Binding, inflator, container) fra Words/Navigation Codelab er brugt som
 * skelet for fragmentet.
 */
class OpeningScreenFragment : Fragment() {

    private var _binding: OpeningFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Når view bliver skabt, sættes en onClickListener til knappen på viewet, som starter et nyt spil.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OpeningFragmentBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.playGame.setOnClickListener {
            (activity as MainActivity).startGame()
        }


        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}