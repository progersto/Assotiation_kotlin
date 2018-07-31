package com.natife.assotiation_kotlin.initgame

import com.natife.assotiation_kotlin.R
import java.util.*

class InitGameRepository : InitGameContract.Repository {

private var listName = mutableListOf<String>()

    override fun createListNamePlayers(): MutableList<String> {
        listName = ArrayList()
        for (i in 0..2) {
            listName.add("")
        }
        return listName
    }

    override fun createListColor(): MutableList<Int> {
        return Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
                R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6)
    }

    override fun addNamePlayerInList(): MutableList<String> {
        listName.add("")
        return listName
    }
}
