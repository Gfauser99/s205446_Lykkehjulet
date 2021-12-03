package com.example.s205446_lykkehjulet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.s205446_lykkehjulet.databinding.GameMainFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainGameFragment : Fragment() {

    private var _binding: GameMainFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {







        // Retrieve and inflate the layout for this fragment
        _binding = GameMainFragmentBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.kategori_text).text = "Category - " + (activity as MainActivity).currentCategory
        view.findViewById<TextView>(R.id.current_points_text).text = "Your points: " + (activity as MainActivity).currentPoints.toString()
        view.findViewById<TextView>(R.id.remaining_lives_text).text = "Remaining lives: " + (activity as MainActivity).currentLives.toString()


        val mActivity = (activity as MainActivity)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 10)
        recyclerView.adapter = RecyclerViewAdapter(mActivity.letterToGuess, requireContext())
        val inputField = view.findViewById<EditText>(R.id.guess_input)
        val inputText = inputField.text
        val guessButton = view?.findViewById<Button>(R.id.guess_button)
        val spinButton = view?.findViewById<Button>(R.id.spin_wheel_button)


        if (mActivity.reSpinButton || mActivity.guessButton) {
            view.findViewById<TextView>(R.id.spin_result_text).text = mActivity.currentSpin
        }
        if (!mActivity.guessButton) {
            guessButton.isEnabled = !guessButton.isEnabled
            inputField.isVisible = false
           /* if (!mActivity.reSpinButton) {
                view.findViewById<TextView>(R.id.spin_result_text).text = mActivity.currentSpin
            }*/
        }
        if (!mActivity.wheelButton && !mActivity.reSpinButton) {
            spinButton.isEnabled = !spinButton.isEnabled
        }

        if (mActivity.guessButton) {
            guessButton?.setOnClickListener {
                println(mActivity.liste + inputText)
                if (inputText.toString().length == 1) {
                    if (inputText.toString().lowercase() !in mActivity.liste
                        && inputText.toString().lowercase() in mActivity.letterToGuess) {
                        mActivity.liste.add(inputText.toString().lowercase())
                        mActivity.currentPoints += (mActivity.pointsFromSpin * (mActivity.letterToGuess.count{it == inputText.toString().lowercase()}))
                       // mActivity.currentSpin = (mActivity.spinnedPoints * (mActivity.letterToGuess.count{it == inputText.toString().lowercase()})).toString()
                        mActivity.guessLetter()
                        mActivity.checkIfWordGuessedOrGameOver()


                    }else if (inputText.toString().lowercase() in mActivity.liste) {
                    }
                    else if (inputText.toString().lowercase() !in mActivity.liste && inputText.toString().length == 1
                        && inputText.toString().lowercase() !in mActivity.letterToGuess) {
                        mActivity.liste.add(inputText.toString().lowercase())
                        mActivity.withdrawAndCheckLifePoint()
                        mActivity.guessLetter()
                        mActivity.checkIfWordGuessedOrGameOver()

                    }


                }






            }}
        if (mActivity.wheelButton) {
            spinButton.setOnClickListener {
                mActivity.spinWheel()
            }}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}