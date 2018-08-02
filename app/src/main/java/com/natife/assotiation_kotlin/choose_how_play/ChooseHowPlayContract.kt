package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context

interface ChooseHowPlayContract {
    interface View {

        fun contextActivity(): Context

        fun showResultDialog()
    }

    interface Presenter {

        fun initPlayerList()

        fun btnAddPlayerClicked()
    }

    interface Repository {

        fun createListNamePlayers(): MutableList<String>

        fun createListColor(): MutableList<Int>
    }
}