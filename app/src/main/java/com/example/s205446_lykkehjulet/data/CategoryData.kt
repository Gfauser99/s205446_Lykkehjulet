package com.example.s205446_lykkehjulet.data

class CategoryData {
    /**
     * Data som indeholder de potentielle kategorier og ord, som spillet bruger.
     * Koden er inspireret fra Affirmations- og Dogglers CodeLab.
     */
    val category_words:  List<Category> =
        listOf(
            Category("Sport", "football"),
            Category("Sport", "tennis"),
            Category("Sport", "badminton"),
            Category("Beer", "tuborg"),
            Category("Beer", "carlsberg"),
            Category("Beer", "heineken"),
            Category("Planets", "venus"),
            Category("Planets", "uranus"),
            Category("Planets", "saturn"),
            Category("Breakfast", "oatmeal"),
            Category("Breakfast", "cornflakes"),
            Category("Breakfast", "toast"),
            Category("Country", "thailand" ),
            Category("Country","singapore" ),
            Category("Country","cambodia" )
            )
}


