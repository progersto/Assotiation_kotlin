package com.natife.assotiation_kotlin.initgame

import android.content.Context

interface InitGameContract {

    interface View {
        fun showListPlayers(listName: MutableList<String>, listColor: MutableList<Int>)

        fun changeScreen(flagChange: Boolean)

        fun contextActivity(): Context
    }

    interface Presenter {
        fun initPlayerList()

        fun btnAddPlayerClicked()

        fun btnNextClicked(difficultLevel: Int)

        fun btnBackClicked()

        fun btnSettingsClicked()

        fun onDestroy()
    }

    interface Repository {
        fun createListNamePlayers(): MutableList<String>

        fun createListColor(): MutableList<Int>

        fun addNamePlayerInList(): MutableList<String>

        fun createListWords(difficultLevel: Int): MutableList<String>
    }
}