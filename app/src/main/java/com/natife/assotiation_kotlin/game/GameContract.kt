package com.natife.assotiation_kotlin.game

import android.content.Context
import com.natife.assotiation_kotlin.initgame.Player



interface GameContract {
    interface View {

        fun contextActivity(): Context

        fun startGame()
        fun finishCurrentGame()
        fun setCircularProgressbar(progress: Int)
        fun setTextTimer(time: String)
    }

    interface Presenter {

        fun playerWin(playerList: MutableList<Player>, winPlayer: Int)
        fun notWin()
        fun initTimer(timerBig: Boolean)
        fun stopCountDownTimer()
        fun getPlayerList(): MutableList<Player>
    }

    interface Repository {

        fun createListNamePlayers(): List<String>?

        fun createListColor(): List<Int>?
    }
}