package com.example.s205446_lykkehjulet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.s205446_lykkehjulet.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {


    /**
     * Navigation- & Bindingrelateret kode er brugt som i Codelab - (Words) eksemplet.
     * Dette går igen i fragmenterne samt adapteren
     */
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
    }
    //ordet, som skal gættes, der er delt op i en liste af ordets bogstaver
    lateinit var letterToGuess: List<String>


    lateinit var currentSpin: String
    lateinit var spinToString: String
    private var letterId : Int = 0
    lateinit var currentCategory : String
    val liste = mutableListOf<String>()

    //Button værdier bruges til henholdsvis at til- og frakoble knapperne samt vise tekst
    //på bestemte tidspunkter
    var guessButton = false
    var wheelButton = false
    var reSpinButton = false

    //Bruges til at vise og opdatere spillerens point og liv
    var currentLives by Delegates.notNull<Int>()
    var currentPoints  by Delegates.notNull<Int>()
    var pointsFromSpin by Delegates.notNull<Int>()
    //Listen af mulige udfald for et hjulspin
    val possibleSpins = listOf("extraTurn", "missTurn", "bankrupt" ,"100" , "200" , "300", "400" , "500" , "750")


    /**
     * Bliver brugt i første fragment, når spillet skal startes.
     * et tilfældigt ord væles fra dataen, og dette splittes op til et array af bogstaver.
     * navControlleren navigerer til MainGameFragmentet og wheel knappen sættes til true og guess til false,
     * da man skal spinne hjulet til start før man kan gætte.
     */
    fun startGame() {

        liste.clear()
        letterId = (CategoryData().category_words.indices).random()
        currentCategory = CategoryData().category_words[letterId].category
        currentPoints = 0
        currentLives = 5
        letterToGuess = CategoryData().category_words[letterId].hiddenWord.chunked(1)
        println(letterToGuess)

        val action = OpeningScreenFragmentDirections.actionLetterListFragmentToWordListFragment(letter = letterToGuess.toString())

       navController.navigate(action)
        wheelButton = true
        guessButton = false
    }

    fun resetGame() {
        liste.clear()
        letterId = (CategoryData().category_words.indices).random()
        currentCategory = CategoryData().category_words[letterId].category

        letterToGuess = CategoryData().category_words[letterId].hiddenWord.chunked(1)
        println(letterToGuess)
        currentPoints = 0
        currentLives = 5
        wheelButton = true
        guessButton = false
        reSpinButton = false
        changeFragmentView(MainGameFragment())
    }

    /**
     * Forskellige udfald fra et spin på hjulet. Bruges når man trykker på 'spin wheel' knappen.
     * currentSpin bliver valgt fra listen over mulige udfald, og ud fra denne værdi bliver
     * en del af when-statemented udført.
     * Metoden gentages indtil der landes på et point-udfald, da de andre blot leder til et nyt spin.
     */
    fun spinWheel() {
        currentSpin = possibleSpins.random()

        when (currentSpin) {
            "extraTurn" -> { currentLives += 1
                spinToString = getString(R.string.extra_spin)
                currentSpin = spinToString
                reSpinButton = true
            }
            "missTurn" -> {currentLives -= 1
                spinToString = getString(R.string.missed_spin)
                currentSpin = spinToString
                reSpinButton = true}

            "bankrupt" -> {currentPoints = 0
                spinToString = getString(R.string.bankrupt_spin)
                currentSpin = spinToString
                reSpinButton = true
            }
            else -> {
                pointsFromSpin = currentSpin.toInt()
                val spinToString = ("$currentSpin possible points for each correct letter! \n Try to guess a letter!")
                currentSpin = spinToString
                wheelButton = false
                reSpinButton = false
                guessButton = true}
        }

        changeFragmentView(MainGameFragment())
        checkIfWordGuessedOrGameOver()


    }




    private fun gameDoneScreen() {
        super.onPostResume()




    }


    /**
     * Bruges når 'Guess' knappen trykkes på. Knappen bliver disabled og wheelButton knappen enables.
     * Derefter bliver fragmentet 'refreshet' så liv, point og korrekte bogstaver opdateres på skærmen.
     */
    fun guessLetter () {
        wheelButton = true
        guessButton = false
        changeFragmentView(MainGameFragment())

    }


    /**
     * Tjekker om alle bogstaver i ordet er gættet eller liv = 0
     */
    fun checkIfWordGuessedOrGameOver() {
        if (liste.containsAll(letterToGuess)) {
            changeFragmentView(WinnerScreenFragment())
        }
        else if (currentLives == 0) {
            changeFragmentView(LosingScreenFragment())
        }
    }

    /**
     * Trækker et liv fra spilleren
     */
    fun withdrawAndCheckLifePoint() {
        currentLives -= 1
        println(currentLives)
        if (currentLives <= 0) {
            gameDoneScreen()
        }



    }

    /**
     * Bruges til at opdatere MainGame fragmentet visuelt, så værdier som liv, point og korrekt gættede ord
     * opdateres på skærmen.
     */
    fun changeFragmentView(fragment: Fragment) {
        val fragmentViewReset = supportFragmentManager.beginTransaction()
        fragmentViewReset.replace(R.id.nav_host_fragment, fragment)

        fragmentViewReset.addToBackStack(null)

        fragmentViewReset.commit()}



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}