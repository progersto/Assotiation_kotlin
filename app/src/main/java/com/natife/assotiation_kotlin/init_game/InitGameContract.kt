package com.natife.assotiation_kotlin.init_game

import android.content.Context

interface InitGameContract {

    interface View {
        fun showListPlayers(listPlayer: MutableList<Player>)

        fun changeScreen(flagChange: Boolean)

        fun contextActivity(): Context

        fun showSettingsDialog(flagStartGame: Boolean)
    }

    interface Presenter {
        fun initPlayerList(listWithName: MutableList<Player>?)

        fun btnAddPlayerClicked()

        fun btnNextClicked(difficultLevel: Int)

        fun btnBackClicked()

        fun btnSettingsClicked()

        fun getFlagChangeScreen(): Boolean

        fun onDestroy()
    }

    interface Repository {
        fun createListPlayer(listWithName: MutableList<Player>?): MutableList<Player>

        fun addNameInPlayerList(): MutableList<Player>

        fun createListWords(difficultLevel: Int, context: Context): MutableList<String>

        fun getCurrentPlayerList(): MutableList<Player>
    }
}