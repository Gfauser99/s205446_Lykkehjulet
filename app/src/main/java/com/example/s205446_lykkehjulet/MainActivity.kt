package com.example.s205446_lykkehjulet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.s205446_lykkehjulet.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
    }
    lateinit var letterToGuess: List<String>
    lateinit var currentSpin: String
    lateinit var spinToString: String
    private var letterId : Int = 0
    var startingLives = 5
    lateinit var currentCategory : String
    var currentLives = startingLives
    //var points = 0
    var guessButton = false
    var wheelButton = false
    var reSpinButton = false
    var currentPoints = 0
    var pointsFromSpin by Delegates.notNull<Int>()
    val possibleSpins = listOf("extraTurn", "missTurn", "bankrupt" ,"100" , "200" , "300", "400" , "500" , "750")

    val liste = mutableListOf<String>()

    fun startGame() {


        liste.clear()
        letterId = (CategoryData().category_words.indices).random()
        currentCategory = CategoryData().category_words[letterId].Category

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
        currentCategory = CategoryData().category_words[letterId].Category

        letterToGuess = CategoryData().category_words[letterId].hiddenWord.chunked(1)
        println(letterToGuess)
        currentPoints = 0
        currentLives = 5
        wheelButton = true
        guessButton = false
        reSpinButton = false
        changeFragmentView(MainGameFragment())
    }

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







    fun guessLetter () {
        wheelButton = true
        guessButton = false
        changeFragmentView(MainGameFragment())

    }

    fun checkIfWordGuessedOrGameOver() {
        if (liste.containsAll(letterToGuess)) {
            changeFragmentView(WinnerScreenFragment())
        }
        else if (currentLives == 0) {
            changeFragmentView(LosingScreenFragment())
        }
    }

    fun withdrawAndCheckLifePoint() {
        currentLives -= 1
        println(currentLives)
        if (currentLives <= 0) {
            gameDoneScreen()
        }



    }

    fun changeFragmentView(fragment: Fragment) {
        val fragmentViewReset = supportFragmentManager.beginTransaction()
        fragmentViewReset.replace(R.id.nav_host_fragment, fragment)

        fragmentViewReset.addToBackStack(null)

        fragmentViewReset.commit()}



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}