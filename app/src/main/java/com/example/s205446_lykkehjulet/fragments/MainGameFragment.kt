package com.example.s205446_lykkehjulet.fragments

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
import com.example.s205446_lykkehjulet.MainActivity
import com.example.s205446_lykkehjulet.R
import com.example.s205446_lykkehjulet.adapter.RecyclerViewAdapter
import com.example.s205446_lykkehjulet.databinding.GameMainFragmentBinding

/**
 * En del af strukturen og koden er enten brugt i / inspireret fra Codelab 'Words'.
 *
 */
class MainGameFragment : Fragment() {

    private var _binding: GameMainFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = GameMainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * I onViewCreated bliver
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Alle værdier som knapper, text og recyclerViewet bliver her opsat til brug,
        // så viewet og logikken kan fungere.
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

        // Her bliver teksten vist, efter man har drejet på hjulet,
        // og wheelButton dermed har værdien false
        if (mActivity.reSpinButton || mActivity.guessButton) {
            view.findViewById<TextView>(R.id.spin_result_text).text = mActivity.currentSpin
        }

        // Dette if-statement bruges til at disable guess-knappen og fjerne tekst-feltet ,
        // når man skal dreje på hjulet, og dermed ikke skal bruge dem.
        //
        if (!mActivity.guessButton) {
            guessButton.isEnabled = !guessButton.isEnabled
            inputField.isVisible = false
            /* if (!mActivity.reSpinButton) {
                view.findViewById<TextView>(R.id.spin_result_text).text = mActivity.currentSpin
            }*/
        }
        // her disables spin knappen, hvis man skal indtaste et bogstav og gætte et ord efter et spin.
        if (!mActivity.wheelButton && !mActivity.reSpinButton) {
            spinButton.isEnabled = !spinButton.isEnabled }

        // OnClickListener på knappen, når den er enabled. Hvis inputtet har en længde på 1 og er et bogstav
        // vil den tjekke om bogstavet findes i ordet eller i listen over gættede ord.
        // Sørger for at give rigtige antal point, hvis bogstav gættes rigtigt,
        // og tjekker om man har vundet/tabt.
        if (mActivity.guessButton) {
            guessButton?.setOnClickListener {

                if (inputText.toString().length == 1) {
                    if (inputText.toString().lowercase() !in mActivity.liste
                        && inputText.toString().lowercase() in mActivity.letterToGuess
                    ) {
                        mActivity.liste.add(inputText.toString().lowercase())
                        mActivity.currentPoints += (mActivity.pointsFromSpin * (mActivity.letterToGuess.count { it == inputText.toString().lowercase() }))
                        // mActivity.currentSpin = (mActivity.spinnedPoints * (mActivity.letterToGuess.count{it == inputText.toString().lowercase()})).toString()
                        mActivity.guessLetter()
                        mActivity.checkIfWordGuessedOrGameOver()}

                    if (inputText.toString().lowercase() !in mActivity.liste
                        && inputText.toString().lowercase() !in mActivity.letterToGuess) {
                            mActivity.liste.add(inputText.toString().lowercase())
                            mActivity.withdrawAndCheckLifePoint()
                            mActivity.guessLetter()
                            mActivity.checkIfWordGuessedOrGameOver()
                    }
                }
            }
        }
        if (mActivity.wheelButton) {
            spinButton.setOnClickListener {
                mActivity.spinWheel()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null }
}