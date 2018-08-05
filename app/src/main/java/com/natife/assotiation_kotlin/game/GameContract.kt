package com.natife.assotiation_kotlin.game

import android.content.Context

interface GameContract {
    interface View {

        fun contextActivity(): Context

        fun showResultDialog()
    }

    interface Presenter {

        fun resultPressed()
    }

    interface Repository {

        fun createListNamePlayers(): List<String>?

        fun createListColor(): List<Int>?
    }
}