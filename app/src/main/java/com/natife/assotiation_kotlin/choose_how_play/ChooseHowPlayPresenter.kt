package com.natife.assotiation_kotlin.choose_how_play

import com.natife.assotiation_kotlin.initgame.InitGameContract
import com.natife.assotiation_kotlin.initgame.InitGameRepository
import com.natife.assotiation_kotlin.initgame.Player
import java.util.*
import android.os.CountDownTimer


class ChooseHowPlayPresenter(private val mView: ChooseHowPlayContract.View) : ChooseHowPlayContract.Presenter {

    private val mRepository: InitGameContract.Repository
    private var playerList = mutableListOf<Player>()
    private var listWords = mutableListOf<String>()
    private var positionWord1 = -1
    private var positionWord2 = -1
    private var name: String? = null
    private var colorPlayer = 0
    private var word: String? = null
    private var positionPlayer: Int = 0
    private lateinit var mCountDownTimer: CountDownTimer
    private val COUNT_DOWN_INTERVAL = 1000


    init {
        this.mRepository = InitGameRepository.getInstance()
    }


    override fun getPlayerList(): MutableList<Player> {
        return mRepository.getCurrentPlayerList()
    }



    override fun buttonGoPressed() {
        mView.startGameActivity(positionPlayer)
    }

    override fun findDataForFillFields(playerList: MutableList<Player>, listWords: MutableList<String>, timeGame: Int) {
        this.playerList = playerList
        this.listWords = listWords
        positionPlayer = getRandom(playerList.size)
        positionWord1 = getRandom(listWords.size)
        positionWord2 = getRandom(listWords.size)
        var word1 = listWords[positionWord1]
        var word2 = listWords[positionWord2]
        name = playerList.get(positionPlayer).name
        name = name!!.substring(0, 1).toUpperCase() + name!!.substring(1)
        word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1)
        word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1)
        colorPlayer = playerList.get(positionPlayer).color
        mView.showData(name!!, colorPlayer, word1, word2)
        startTimerGame(timeGame);
    }

    private fun getRandom(size: Int): Int {
        val rand = Random()
        val position = rand.nextInt(size)

        if (positionWord1 != -1 && positionWord1 == position) {
            getRandom(size)
        }
        return position
    }// getRandom


    private fun startTimerGame(timeGame: Int) {
        mCountDownTimer = object : CountDownTimer(((timeGame + 1) * 1000).toLong(), COUNT_DOWN_INTERVAL.toLong()) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                mView.timeGameOver()
            }
        }
        mCountDownTimer.start()
    }

    override fun stopTimerGame() {
        mCountDownTimer.cancel()
    }




}