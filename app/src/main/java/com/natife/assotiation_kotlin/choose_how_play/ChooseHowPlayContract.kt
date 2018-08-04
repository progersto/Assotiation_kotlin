package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context

interface ChooseHowPlayContract {
    interface View {

        fun contextActivity(): Context

        fun showResultDialog()

        fun showData(name: String, color: Int, word1: String, word2: String)
    }

    interface Presenter {

        fun word1Pressed(word: String)

        fun word2Pressed(word: String)

        fun layoutShow_Pressed()

        fun layoutTell_Pressed()

        fun layoutDraw_Pressed()

        fun resultPressed()

        fun buttonGo()

        fun findDataForFillFields(listName: MutableList<String>, listColor: MutableList<Int>, listWords: MutableList<String>)
    }

    interface Repository {

        fun createListNamePlayers(): MutableList<String>

        fun createListColor(): MutableList<Int>

    }
}