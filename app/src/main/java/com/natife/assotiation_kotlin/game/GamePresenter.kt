package com.natife.assotiation_kotlin.game

import android.os.CountDownTimer
import android.util.Log
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.init_game.InitGameContract
import com.natife.assotiation_kotlin.init_game.InitGameRepository
import com.natife.assotiation_kotlin.init_game.Player
import java.util.*
import java.util.concurrent.TimeUnit

class GamePresenter(private val mView: GameContract.View) : GameContract.Presenter {

    private val mRepository: InitGameContract.Repository = InitGameRepository.getInstance()
    private lateinit var mCountDownTimer: CountDownTimer
    private val countDownInterval = 1000


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
                        ) { _, _ -> mView.dialogTimeMoveGone(true) }
                        .setNegativeButton(mView.contextActivity().resources.getString(R.string.they_not_guessed)
                        ) { _, _ -> mView.dialogTimeMoveGone(false) }
                        .setCancelable(false)
                        .show()
            }
        }
        mCountDownTimer.start()
    }

    override fun stopCountDownTimer() {
        mCountDownTimer.cancel()
    }

    override fun gameActivityDestroyed() {
        mRepository.startRefresh()
    }
}
