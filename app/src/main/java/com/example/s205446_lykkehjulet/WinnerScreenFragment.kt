package com.example.s205446_lykkehjulet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s205446_lykkehjulet.databinding.WinnerScreenFragmentBinding

class WinnerScreenFragment: Fragment() {
    private var _binding: WinnerScreenFragmentBinding? = null

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
        _binding = WinnerScreenFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mActivity = (activity as MainActivity)
        binding.gameWon.text
        binding.winPlayAgainButton.setOnClickListener {
            mActivity.resetGame()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}