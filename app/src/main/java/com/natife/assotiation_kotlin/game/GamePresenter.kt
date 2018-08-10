package com.natife.assotiation_kotlin.game

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.util.Log
import android.view.Window
import android.widget.RelativeLayout
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.initgame.InitGameContract
import com.natife.assotiation_kotlin.initgame.InitGameRepository
import com.natife.assotiation_kotlin.initgame.Player
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MINUTES
import javax.xml.datatype.DatatypeConstants.HOURS


class GamePresenter//передаем экземпляр View
(private val mView: GameContract.View) : GameContract.Presenter {
    private val mRepository: InitGameContract.Repository
    private val timeGame: Int = 0
    private val numberLap: Int = 0
    private var mCountDownTimer: CountDownTimer? = null
    private val countDownInterval = 1000

    init {
        this.mRepository = InitGameRepository.getInstance()
    }


    override fun getPlayerList(): MutableList<Player> {
        return mRepository.getCurrentPlayerList()
    }


    override fun playerWin(playerList: MutableList<Player>, winPlayer: Int, positionGuessingPlayer: Int) {
        val score = playerList[winPlayer].countScore + 1
        val countWords = playerList[winPlayer].countWords + 1
        val scoreGuessingPlayer = playerList[positionGuessingPlayer].countScore + 1
        playerList[positionGuessingPlayer].countScore = scoreGuessingPlayer
        playerList[winPlayer].countScore = score
        playerList[winPlayer].countWords = countWords
        //        numberLap -= 1;
        mView.finishCurrentGame()
    }

    override fun notWin() {
        mView.finishCurrentGame()
    }

    override fun initTimer(timerBig: Boolean, timeMove:Int) {
        mCountDownTimer = object : CountDownTimer(((timeMove+1) * 1000).toLong(), countDownInterval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tick of Progress$millisUntilFinished")
                if (timerBig) {
                    val progress = timeMove - millisUntilFinished.toInt() / 1000
                    mView.setCircularProgressbar(progress)
                }
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                mView.setTextTimer(String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds))
            }

            override fun onFinish() {
                android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                        .setTitle(mView.contextActivity().resources.getString(R.string.time_gone))
                        .setMessage(mView.contextActivity().resources.getString(R.string.word_is_guessed))
                        .setPositiveButton(mView.contextActivity().resources.getString(R.string.they_guessed)
                        ) { dialog, button -> mView.dialogTimeMoveGone(true) }
                        .setNegativeButton(mView.contextActivity().resources.getString(R.string.they_not_guessed)
                        ) { dialog, button -> mView.dialogTimeMoveGone(false) }
                        .setCancelable(false)
                        .show()
            }
        }
        mCountDownTimer!!.start()
    }

    override fun stopCountDownTimer() {
        mCountDownTimer!!.cancel()
    }
}
