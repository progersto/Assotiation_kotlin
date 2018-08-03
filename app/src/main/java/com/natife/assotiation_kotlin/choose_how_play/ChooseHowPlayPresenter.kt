package com.natife.assotiation_kotlin.choose_how_play

import java.util.*

class ChooseHowPlayPresenter(private val mView: ChooseHowPlayContract.View) : ChooseHowPlayContract.Presenter {
    private val mRepository: ChooseHowPlayContract.Repository
    private var listName = mutableListOf<String>()
    private var listColor = mutableListOf<Int>()
    private var listWords = mutableListOf<String>()
    private var positionWord1 = -1
    private var positionWord2 = -1


    init {
        this.mRepository = ChooseHowPlayRepository()
    }


    override fun word1Pressed(word: String) {

    }

    override fun word2Pressed(word: String) {

    }

    override fun layoutShow_Pressed() {

    }

    override fun layoutTell_Pressed() {

    }

    override fun layoutDraw_Pressed() {

    }

    override fun buttonGo() {

    }

    override fun findDataForFillFields(listName: MutableList<String>, listColor: MutableList<Int>, listWords: MutableList<String>) {
        this.listName = listName
        this.listColor = listColor
        this.listWords = listWords
        val positionPlayer = getRandom(listName.size)
        positionWord1 = getRandom(listWords.size)
        positionWord2 = getRandom(listWords.size)
        var word1 = listWords[positionWord1]
        var word2 = listWords[positionWord2]
        var name = listName[positionPlayer]
        word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1)
        word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1)
        name = name.substring(0, 1).toUpperCase() + name.substring(1)
        mView.showData(name, listColor[positionPlayer], word1, word2)
    }

    private fun getRandom(size: Int): Int {
        val rand = Random()
        val position = rand.nextInt(size)

        if (positionWord1 != -1 && positionWord1 == position) {
            getRandom(size)
        }

        return position
    }// getRandom




}