package com.natife.assotiation_kotlin.game

import android.content.Context
import com.natife.assotiation_kotlin.init_game.Player


interface GameContract {
    interface View {

        fun contextActivity(): Context

        fun finishCurrentGame()
        fun setCircularProgressbar(progress: Int)
        fun setTextTimer(time: String)
        fun dialogTimeMoveGone(flag: Boolean)
    }

    interface Presenter {

        fun playerWin(playerList: MutableList<Player>, winPlayer: Int, positionGuessingPlayer: Int)
        fun notWin()
        fun initTimer(timerBig: Boolean, timeMove: Int)
        fun stopCountDownTimer()
        fun getPlayerList(): MutableList<Player>
    }
}