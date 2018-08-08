package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context
import com.natife.assotiation_kotlin.initgame.Player

interface ChooseHowPlayContract {
    interface View {

        fun startGameActivity(posPlayer: Int)

        fun showResultDialog()

        fun showData(name: String, color: Int, word1: String, word2: String)

        fun timeGameOver()
    }

    interface Presenter {

        fun buttonGoPressed()

        fun findDataForFillFields(playerList: MutableList<Player>, listWords: MutableList<String>, timeGame: Int)

        fun getPlayerList(): MutableList<Player>

        fun stopTimerGame()
    }

    interface Repository {

        fun createListNamePlayers(): MutableList<String>

        fun createListColor(): MutableList<Int>

    }
}