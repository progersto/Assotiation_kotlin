package com.natife.assotiation_kotlin.initgame

import android.content.Context
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.utils.ListGenerator
import java.util.*
import kotlin.collections.ArrayList

class InitGameRepository : InitGameContract.Repository {
    private val colorList = java.util.ArrayList(Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
            R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6))
    private var playerList = mutableListOf<Player>()

    override fun addNameInPlayerList(): MutableList<Player> {
        playerList.add(Player("", colorList.get(playerList.size + 1), 0, 0))
        return playerList
    }


    override fun createListWords(difficultLevel: Int, context: Context): MutableList<String> {
        return ListGenerator.createListSelectedLevel(context, difficultLevel)
    }


    override fun createListPlayer(listWithName: MutableList<Player>?): MutableList<Player> {
        playerList = java.util.ArrayList()
        if (listWithName == null) {
            for (i in 0..2) {
                playerList.add(Player("", colorList[i], 0, 0))
            }
        } else {
            for (i in listWithName.indices) {
                playerList.add(Player(listWithName[i].name!!, colorList[i], 0, 0))
            }
        }
        return playerList
    }

}
