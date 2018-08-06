package com.natife.assotiation_kotlin.choose_how_play

import android.content.Intent
import com.natife.assotiation_kotlin.game.GameActivity
import com.natife.assotiation_kotlin.initgame.Player
import java.util.*

class ChooseHowPlayPresenter(private val mView: ChooseHowPlayContract.View) : ChooseHowPlayContract.Presenter {

    private val mRepository: ChooseHowPlayContract.Repository
    private var playerList = mutableListOf<Player>()
    private var listWords = mutableListOf<String>()
    private var positionWord1 = -1
    private var positionWord2 = -1
    private var intent: Intent? = null
    private var name: String? = null
    private var colorPlayer = 0
    private var word: String? = null


    init {
        this.mRepository = ChooseHowPlayRepository()
    }


    override fun word1Pressed(word: String) {
        this.word = word
    }

    override fun word2Pressed(word: String) {
        this.word = word
    }

    override fun layoutShow_Pressed() {
        intent!!.putExtra("how_explain", "show")
    }

    override fun layoutTell_Pressed() {
        intent!!.putExtra("how_explain", "tell")
    }

    override fun layoutDraw_Pressed() {
        intent!!.putExtra("how_explain", "draw")
    }


    override fun buttonGo() {
        intent!!.putStringArrayListExtra("listWords", listWords as ArrayList<String>)
        intent!!.putExtra("colorPlayer", colorPlayer)
        intent!!.putExtra("name", name)
        intent!!.putExtra("word", word)
        mView.contextActivity().startActivity(intent)
    }

    override fun findDataForFillFields(playerList: MutableList<Player>, listWords: MutableList<String>) {
        this.playerList = playerList
        this.listWords = listWords
        val positionPlayer = getRandom(playerList.size)
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
        intent = Intent(mView.contextActivity(), GameActivity::class.java)
    }

    private fun getRandom(size: Int): Int {
        val rand = Random()
        val position = rand.nextInt(size)

        if (positionWord1 != -1 && positionWord1 == position) {
            getRandom(size)
        }

        return position
    }// getRandom

    override fun resultPressed() {
        mView.showResultDialog()
    }


}