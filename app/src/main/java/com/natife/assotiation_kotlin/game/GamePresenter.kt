package com.natife.assotiation_kotlin.game

import android.os.CountDownTimer
import android.util.Log
import com.natife.assotiation_kotlin.initgame.Player
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MINUTES
import javax.xml.datatype.DatatypeConstants.HOURS



class GamePresenter//передаем экземпляр View
(private val mView: GameContract.View) : GameContract.Presenter {
    private val mRepository: GameContract.Repository
    private val timeMove: Int = 0
    private val timeGame: Int = 0
    private val numberLap: Int = 0
    private var mCountDownTimer: CountDownTimer? = null

    init {
        this.mRepository = GameRepository()
    }


    override fun playerWin(playerList: MutableList<Player>, winPlayer: Int) {
        val score = playerList[winPlayer].countScore + 1
        val countWords = playerList[winPlayer].countWords + 1
        playerList[winPlayer].countScore.plus(1)
        playerList[winPlayer].countWords.plus(1)
        //        numberLap -= 1;
        mView.finishCurrentGame()
    }

    override fun notWin() {
        mView.finishCurrentGame()
    }

    override fun initTimer(timerBig: Boolean) {
        mCountDownTimer = object : CountDownTimer((61 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tick of Progress$millisUntilFinished")
                if (timerBig) {
                    val progress = 60 - millisUntilFinished.toInt() / 1000
                    mView.setCircularProgressbar(progress)
                }
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                mView.setTextTimer(String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds))
            }

            override fun onFinish() {}
        }
        mCountDownTimer!!.start()
    }

    override fun stopCountDownTimer() {
        mCountDownTimer!!.cancel()
    }
}
